package com.hancook.dealcrawler;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

@NoArgsConstructor
@Slf4j
/* Multi-threaded crawler */
public class SeaCrawler implements Runnable {

    private final String uri = "http://localhost:8090/kafka/deal?deal=";
    private boolean flag = false; // 수집 날짜가 아닌 다른 날짜의 데이터를 받아오는 경우를 표시
    private String date;
    private int page;
    private int number;

    public SeaCrawler(int page, int number) {
        this.page = page;
        this.number = number;
    }

    // 입력 시 date의 형태는 yyyy-MM-dd 형식
    // startPage는 읽고싶은 페이지부터
    public SeaCrawler(String date, int page, int number) {
        this.date = date;
        this.page = page;
        this.number = number;
    }

    @Override
    public void run() {
        init();
    }

    private void init() {

        try {
            String itemcode = "611";
            String kindcode = "03";
            String today = "2023-03-28";
            String formatDay = "20230328";
            String large = "수산물";
            String pageUrl = "https://www.kamis.or.kr/customer/price/fisheriesRetail/period.do?action=daily&itemcategorycode=600&itemcode=" +
                    itemcode + "&kindcode=" + kindcode + "&startday=" + today + "&endday=" + today;
            Document doc = null;

            doc = Jsoup.connect(pageUrl).timeout(30000).get();

            Elements element = doc.select(".clear > .s_tit6");
            String name = element.first().text();
            name = name.replaceAll("[/,]", "");
            StringTokenizer stringN = new StringTokenizer(name);
            String small = stringN.nextToken();
            String medium = stringN.nextToken();
            String origin = "korea";
           if(medium.contains("수입")){
               origin="income";
           }

            Elements pElements = doc.select(".wtable3 > tbody > tr > .last");

            for(int i=0;i<pElements.size();i++){

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
//        try {
//            String filepath = "src\\main\\resources\\sea.csv";
//            File wFile = new File(filepath);
//            String NEWLINE = System.lineSeparator();
//            BufferedWriter bw = new BufferedWriter(new FileWriter(wFile));
//            bw.write("deal_date,large,medium,소,Kg,income,가격");
//            bw.write(NEWLINE);
//
//            Date day = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
//
//            // 날짜 포매팅
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            date = dateFormat.format(day);
//
//            File file = new File("src\\main\\resources\\search.txt");
//            FileReader fileReader = null;
//            fileReader = new FileReader(file);
//            BufferedReader bufReader = new BufferedReader(fileReader);
//            String list = "";
//            while ((list = bufReader.readLine()) != null) {
//                StringTokenizer st = new StringTokenizer(list);
//                String itemcode = st.nextToken();
//                String kindcode = st.nextToken();
//                String today = date;
//                String formatDay = today.replaceAll("-", "");
////        // Jsoup를 이용해서 크롤링
//                String pageUrl = "https://www.kamis.or.kr/customer/price/livestockRetail/period.do?action=daily&itemcategorycode=500&productrankcode=0&itemcode=" +
//                        itemcode + "&kindcode=" + kindcode + "&startday=" + today + "&endday=" + today;
//
//                Beef beef = new Beef();
//                beef.setDate(formatDay);
//
//                Document doc = Jsoup.connect(pageUrl).timeout(30000).get();
//                Elements elements = doc.select(".clear > .s_tit6");
//                Elements pElements = doc.select(".wtable3 > tbody > tr > .last");
//                for (int i = 0; i < elements.size(); i++) {
//                    if (i % 3 == 0) {
//                        String name = elements.get(i).text();
//                        name = name.replaceAll("[/,]", "");
//                        StringTokenizer stringN = new StringTokenizer(name);
//                        String large = stringN.nextToken();
//                        String medium = stringN.nextToken();
//                        beef.setLarge(large);
//                        beef.setMedium(medium);
//                    } else if (i % 3 == 1) {
//                        String name = elements.get(i).text();
//                        name = name.replaceAll("등급 : ", "");
//                        name = name.replaceAll(",", "");
//                        beef.setOrigin(name);
//                        System.out.println(name);
//
//                    } else {
//                        int index = i / 3;
//                        if (pElements.size() <= (index * 21) + 1) {
//                            continue;
//                        }
//                        String avg = pElements.get((index * 21) + 1).text();
//                        String max = pElements.get((index * 21) + 2).text();
//                        String min = pElements.get((index * 21) + 3).text();
//                        bw.write(formatDay + "," + beef.getLarge() + "," + beef.getMedium() +
//                                "," + beef.getOrigin() + ",korea," + max + "," + min + "," + avg);
//                        bw.write(NEWLINE);
//                        System.out.println(avg);
//                    }
//                }
//            }
//            bw.flush();
//            bw.close();
//
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }
}
