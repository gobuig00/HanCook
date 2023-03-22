package com.hancook.dealcrawler;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * MultiCrawler 클래스는 크롤러를 멀티스레드로 실행하며,
 * 각 스레드에서는 Jsoup를 이용하여 웹 페이지를 읽어들여 정보를 추출합니다.
 * 추출한 정보는 Kafka Producer를 이용하여 Kafka 클러스터에 전송합니다.
 */

@NoArgsConstructor
@Slf4j
public class MultiCrawler implements Runnable {

    //     Kafka Producer의 주소 저장, localhost의 8090 포트에서 동작
    private final String uri = "http://localhost:8090/kafka/deal?deal=";
    //    private final String uri = "http://localhost:8090/kafka/deal";
    private boolean flag = false; // 수집 날짜가 아닌 다른 날짜의 데이터를 받아오는 경우를 표시
    private String date;
    private int page; // 크롤링하려는 페이지 번호
    private int number; // 크롤링하려는 페이지 수

    public MultiCrawler(int page, int number) {
        this.page = page;
        this.number = number;
    }

    // 입력 시 date의 형태는 yyyy-MM-dd 형식
    // startPage는 읽고싶은 페이지부터
    public MultiCrawler(String date, int page, int number) {
        this.date = date;
        this.page = page;
        this.number = number;
    }

    // 스레드에서 실행, init 메서드 호출
    @Override
    public void run() {
        init();
    }

    // 크롤링 실행하는 메서드
    private void init() {

        if (date == null || date.isEmpty()) {
            // 수집 날짜 구하기 - 매일 어제의 경매데이터를 읽어옴
            Date today = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);

            // 날짜 포매팅
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.format(today);
        }

        // 파일 이름으로 사용할 날짜
        String outputDateFormatStr = date.replace("-", "");

        // Jsoup를 이용해서 크롤링
        // 각 스레드에서는 Jsoup를 이용하여 웹 페이지를 읽어들여 정보를 추출
        String beforePageUrl = "https://at.agromarket.kr/domeinfo/sanRealtime.do?pageNo=";
        String afterPageUrl = "&saledateBefore=" + date +
                "&largeCdBefore=&midCdBefore=&smallCdBefore=&saledate=" + date +
                "&whsalCd=&cmpCd=&sanCd=&smallCdSearch=&largeCd=&midCd=&smallCd=";

        // Jsoup을 사용하여 읽은 HTML 문서
        Document doc = null;

        try {
            // 크롤링할 페이지 저장
            while (!flag || page < number + 500) { // 500페이지씩 읽어옴
                // 1일치 만큼만 데이터 읽어오기
                String url = beforePageUrl + page++ + afterPageUrl;
                System.out.println(url); // 어떤 페이지를 읽고있는지 확인하기 위해 필요
                doc = Jsoup.connect(url).timeout(30000).get(); // HTML 문서 가져오기
                readContents(outputDateFormatStr, doc); // HTML 문서에서 데이터를 추출
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // HTML 문서에서 데이터를 추출하는 메서드
    // 각 행 경락일시, 부류, 품목, 품종, 수입여부(0: income, 1: korea), 규격(0: kg, 1: 개), 경락가 순
    private void readContents(String outputDateFormatStr, Document doc) {
        // doc.select() 메서드를 사용하여 원하는 태그를 선택
        Elements element = doc.select("div.table_x_scroll");

        // iterator를 사용하여 <tr> <td>에 해당하는 값을 가져오기
        Iterator<Element> iterator = element.select("tr").iterator();

        // 맨 위 열이름 제거
        iterator.next();

        while (iterator.hasNext()) {
//            System.out.println(iterator.next().text());
            // splitContent 메서드를 사용하여 값을 추출
            splitContent(outputDateFormatStr, iterator.next().children());

            // 현재 날짜의 데이터가 아닌 경우, 더이상 데이터를 읽어올 필요가 없다.
            if (flag) return;
        }
    }

    // HTML 문서에서 데이터를 추출하는 메서드
    // 자식 태그가 아닌, iterator.next().text()를 사용하였을 경우 값이 없다면 ' '이 출력되기 때문에
    // ' '으로 split 할 때, 자식 개수가 일정하지 않을 수 있음
    // <tr> 아래 자식 태그들의 각각 값을 확인.
    // 또한, 자식의 전체를 하나의 스트링으로 나타내는 것보다 파싱 시, 글자 하나하나를 한 번만 살펴보면 되어 시간이 줄어듦
    private void splitContent(String outputDateFormatStr, Elements children) {
        // 추출한 데이터를 저장할 StringBuilder
        StringBuilder sb = new StringBuilder();
        // children.get(0)에서 추출한 경매일시에서 날짜 부분을 의미
        // replaceAll("-", "")를 사용하여 날짜에서 하이픈을 제거
        String date = children.get(0).text().split(" ")[0].replaceAll("-", "");
        // outputDateFormatStr과 비교하여 오늘 날짜와 같은지 확인
        // 오늘 날짜가 아닌 경우 flag를 true로 변경하고 메서드를 종료
        if (children.get(0).text().isEmpty() || date.equals(outputDateFormatStr)) {
            sb.append(date).append(", ");
        } else {
            flag = true;
            return;
        }

        // sb에 부류, 품목 추가
        for (int i = 3; i < 5; i++) {
            sb.append(children.get(i).text()).append(", ");
        }

        // 품종의 경우 '('가 포함되어 있거나, ','가 포함되어 있다면 제거하고 '('나, ',' 앞 문자들만 취급
        String[] type = children.get(5).text().split("[(,]");
        // 괄호 또는 쉼표 이전의 품종 이름
        sb.append(type[0]).append(", ");

        // 수입일 경우 0, 국내산이면 1을 sb에 추가
        if (type.length > 1 && type[1].charAt(0) == '수')
            sb.append(0).append(", ");
        else
            sb.append(1).append(", ");


        // 규격 - kg -> 100g 단위 - 규격의 종류에 0, 개수인 경우 규격의 여부에 1 표시
        String kg = null;
        boolean isKg = false;

        // 0.5kg, 500g -> kg
        // kg가 k를 포함하면 k 앞의 숫자를 kg으로 지정
        if (children.get(7).text().contains("k")) {
            kg = children.get(7).text().split("k")[0];
            isKg = true;
        }
        // kg가 g을 포함하면 g 앞의 숫자를 100으로 나누기
        else if (children.get(7).text().contains("g")) {
            kg = "0." + children.get(7).text().split("g")[0];
            isKg = true;
        }
        // 그렇지 않은 경우 숫자만 추출
        else {
            kg = children.get(7).text().replaceAll("[^0-9]", "");
            isKg = false;
        }

        // 규격의 단위에 따라 값을 계산하는 데 사용
        int byNumber = 1;

        if (isKg) {
            sb.append("0, ");
            byNumber = (int) (Float.parseFloat(kg) * 10);
        } else { // 단위가 개수인 경우,
            sb.append("1, ");
            byNumber = Integer.parseInt(kg);
        }
//        sb.append(byNumber).append(", "); 규격(100g or 개)


        // 가격
        float price = 0;
        try {
            // NumberFormat으로 콤마(,) 제거하고 floatValue()를 호출하여 float 형태의 가격 값 추출
            price = NumberFormat.getNumberInstance(Locale.KOREA).parse(children.get(9).text()).floatValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // isKg가 true인 경우 price 값을 byNumber로 나눈 값을 sb에 추가
        // 그렇지 않은 경우 price 값을 sb에 추가
        sb.append(isKg ? price / (byNumber == 0 ? 1 : byNumber) : price).append("\n");
        // after reading one row of a table, This server should send kafka producer
        // RedisTemplate을 사용하여 Kafka Producer에 데이터 전송
        try {
            TimeUnit.MILLISECONDS.sleep(10);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForObject(uri + date, String.class);
//            writeFile(date, sb);
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeFile(String date, StringBuilder sb) {
        try (PrintWriter writer = new PrintWriter(new File("./src/main/resources/" + date + "_" + number + ".csv"))) {
            writer.write(sb.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
