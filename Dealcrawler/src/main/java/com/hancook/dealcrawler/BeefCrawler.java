package com.hancook.dealcrawler;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.client.RestTemplate;

import javax.print.Doc;
import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor
@Slf4j
/* Multi-threaded crawler */
public class BeefCrawler implements Runnable {

    private final String uri = "http://localhost:8090/kafka/deal?deal=";
    private boolean flag = false; // 수집 날짜가 아닌 다른 날짜의 데이터를 받아오는 경우를 표시
    private String date;
    private int page;
    private int number;

    public BeefCrawler(int page, int number) {
        this.page = page;
        this.number = number;
    }

    // 입력 시 date의 형태는 yyyy-MM-dd 형식
    // startPage는 읽고싶은 페이지부터
    public BeefCrawler(String date, int page, int number) {
        this.date = date;
        this.page = page;
        this.number = number;
    }

    @Override
    public void run() {
        init();
    }

    private void init() {

//        if(date == null || date.isEmpty()) {
//            // 수집 날짜 구하기 - 매일 어제의 경매데이터를 읽어옴
//            Date today = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
//
//            // 날짜 포매팅
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            date = dateFormat.format(today);
//        }
//
//        // output file에 쓸 날짜 포매팅
//        String outputDateFormatStr = date.replace("-", "");
//
        try {
            String filepath = "src\\main\\resources\\beef.csv";
            File wFile = new File(filepath);
            String NEWLINE = System.lineSeparator();
            BufferedWriter bw = new BufferedWriter(new FileWriter(wFile));
            bw.write("deal_date,large,medium,소,Kg,income,가격");
            bw.write(NEWLINE);

            Date day = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);

            // 날짜 포매팅
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.format(day);

            File file = new File("src\\main\\resources\\search.txt");
            FileReader fileReader = null;
            fileReader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(fileReader);
            String list="";
            while ((list = bufReader.readLine()) != null) {
                StringTokenizer st= new StringTokenizer(list);
                String itemcode = st.nextToken();
                String kindcode = st.nextToken();
                String today = date;
                String formatDay = today.replaceAll("-", "");
//        // Jsoup를 이용해서 크롤링
                String pageUrl = "https://www.kamis.or.kr/customer/price/livestockRetail/period.do?action=daily&itemcategorycode=500&productrankcode=0&itemcode=" +
                        itemcode + "&kindcode=" + kindcode + "&startday=" + today + "&endday=" + today;

                Beef beef = new Beef();
                beef.setDate(formatDay);

                Document doc = Jsoup.connect(pageUrl).timeout(30000).get();
                Elements elements = doc.select(".clear > .s_tit6");
                Elements pElements = doc.select(".wtable3 > tbody > tr > .last");
                for (int i = 0; i < elements.size(); i++) {
                    if (i % 3 == 0) {
                        String name = elements.get(i).text();
                        name = name.replaceAll("[/,]", "");
                        StringTokenizer stringN = new StringTokenizer(name);

                        String large = stringN.nextToken();
                        String medium = stringN.nextToken();
                        beef.setLarge(large);
                        beef.setMedium(medium);
                    } else if (i % 3 == 1) {
                        String name = elements.get(i).text();
                        name = name.replaceAll("등급 : ", "");
                        name = name.replaceAll(",", "");
                        beef.setOrigin(name);
                        System.out.println(name);

                    } else {
                        int index = i / 3;
                        if(pElements.size()<=(index * 21)+1){
                            continue;
                        }
                        String avg = pElements.get((index * 21) + 1).text();
                        String max = pElements.get((index * 21) + 2).text();
                        String min = pElements.get((index * 21) + 3).text();
                        bw.write(formatDay + "," + beef.getLarge() + "," + beef.getMedium() +
                                "," + beef.getOrigin() + ",korea," + max + "," + min + "," + avg);
                        bw.write(NEWLINE);
                        System.out.println(avg);
                    }
                }
            }
            bw.flush();
            bw.close();


//            for (Element element : elements) {
//                if(index%3==0){
//                    String name = element.text();
//                    name=name.replaceAll("[/,]","");
//                    System.out.println(name);
//                    StringTokenizer st= new StringTokenizer(name);
//
//                    origin = st.nextToken();
//                    System.out.println(large+" "+origin);
//                }else if(index%3==1){
//
//                }
//                index++;
//
//
//            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        //&pageSize=10
//
//        Document doc = null;
//
//        try {
//            int tempN=page;
//            while(page < tempN + 500) { // 500페이지씩 읽어옴
//                // 1일치 만큼만 데이터 읽어오기
//                System.out.println("page : "+page+"number : "+(number));
//
//                String url = beforePageUrl + page++ + afterPageUrl;
//                //System.out.println(url); // 어떤 페이지를 읽고있는지 확인하기 위해 필요
//
//                doc = Jsoup.connect(url).timeout(30000).get();
//                readContents(outputDateFormatStr, doc);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /*
    각 행 경락일시, 부류, 품목, 품종, 수입여부(0: income, 1: korea), 규격(0: kg, 1: 개), 경락가 순
     */
//    private void readContents(String outputDateFormatStr, Document doc) {
//        //select를 이용하여 원하는 태그를 선택한다.
//        Elements element = doc.select("div.table_x_scroll");
//
//        //Iterator을 사용하여 <tr> > <td> 에 해당하는 값 가져오기
//        Iterator<Element> iterator = element.select("tr").iterator();
//
//        // 맨 위 열이름 제거
//        iterator.next();
//
//        while (iterator.hasNext()) {
////            System.out.println(iterator.next().text());
//            splitContent(outputDateFormatStr, iterator.next().children());
//
//            // 현재 날짜의 데이터가 아닌 경우, 더이상 데이터를 읽어올 필요가 없다.
//            if(flag) return;
//        }
//    }
//
//
//    // 자식 태그가 아닌, iterator.next().text()를 사용하였을 경우 값이 없다면 ' '이 출력되기 때문에
//    // ' '으로 split 할 때, 자식 개수가 일정하지 않을 수 있음
//    // <tr> 아래 자식 태그들의 각각 값을 확인.
//    // 또한, 자식의 전체를 하나의 스트링으로 나타내는 것보다 파싱 시, 글자 하나하나를 한 번만 살펴보면 되어 시간이 줄어듦
//    private void splitContent(String outputDateFormatStr, Elements children) {
//
//        StringBuilder sb = new StringBuilder();
//        String date = children.get(0).text().split(" ")[0].replaceAll("-", "");
//        // 오늘 날짜가 아닌 경우 오늘 날짜의 csv 파일에 포함시키지 않음
//        if(children.get(0).text().isEmpty() || date.equals(outputDateFormatStr)) {
//            sb.append(date).append(", ");
//        } else {
//            flag = true;
//            return;
//        }
//
//        // 부류, 품목
//        for(int i = 3; i < 5; i++) {
//            sb.append(children.get(i).text()).append(", ");
//        }
//
//        // 품종의 경우 '('가 포함되어 있거나, ','가 포함되어 있다면 제거하고 '('나, ',' 앞 문자들만 취급
//        String[] type = children.get(5).text().split("[(,]");
//        sb.append(type[0]).append(", ");
//
//        // 수입일 경우 0, 수입이 아닐 경우 1
//        if(type.length > 1 && type[1].charAt(0) == '수')
//            sb.append(0).append(", ");
//        else
//            sb.append(1).append(", ");
//
//
//        // 규격 - kg -> 100g 단위 - 규격의 종류에 0, 개수인 경우 규격의 여부에 1 표시
//        String kg = null;
//        boolean isKg = false;
//
//        // 0.5kg, 500g -> kg
//        if(children.get(7).text().contains("k")){
//            kg = children.get(7).text().split("k")[0];
//            isKg = true;
//        } else if(children.get(7).text().contains("g")) {
//            kg = "0."+ children.get(7).text().split("g")[0];
//            isKg = true;
//        } else {
//            kg = children.get(7).text().replaceAll("[^0-9]", "");
//            isKg = false;
//        }
//
//        int byNumber = 1;
//
//        if(isKg) {
//            sb.append("0, ");
//            byNumber = (int) (Float.parseFloat(kg) * 10);
//        } else { // 단위가 개수인 경우,
//            sb.append("1, ");
//            byNumber = Integer.parseInt(kg);
//        }
////        sb.append(byNumber).append(", "); 규격(100g or 개)
//
//
//        // 가격
//        float price = 0;
//        try {
//            price = NumberFormat.getNumberInstance(Locale.KOREA).parse(children.get(9).text()).floatValue();
//        } catch(ParseException e) {
//            e.printStackTrace();
//        }
//        sb.append(isKg ? price / (byNumber == 0 ? 1 : byNumber) : price).append("\n");
//        // after reading one row of a table, This server should send kafka producer
//        try {
//            TimeUnit.MILLISECONDS.sleep(10);
//            RestTemplate restTemplate = new RestTemplate();
//            restTemplate.getForObject(uri + sb.toString(), String.class);
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }

    /*private void writeFile(String date) {
        try (PrintWriter writer = new PrintWriter(new File("./src/main/resources/"+ date + "/" + date + "_" + number + ".csv"))) {
            writer.write(sb.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }*/
}
