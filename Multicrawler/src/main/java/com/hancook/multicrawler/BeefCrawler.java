package com.hancook.multicrawler;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

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

        try {
            Date day = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
            String filepath = "./data/beef/beef.csv";
            File wFile = new File(filepath);
            String NEWLINE = System.lineSeparator();
            BufferedWriter bw = new BufferedWriter(new FileWriter(wFile));




            // 날짜 포매팅
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.format(day);


//            File file = new File("src\\main\\resources\\search.txt");
//            FileReader fileReader = null;
//            fileReader = new FileReader(file);
//            BufferedReader bufReader = new BufferedReader(fileReader);
            String code="4301 21\n" +
                    "4301 22\n" +
                    "4301 36\n" +
                    "4301 40\n" +
                    "4301 50\n" +
                    "4304 25\n" +
                    "4304 27\n" +
                    "4304 28\n" +
                    "4304 68\n" +
                    "4401 31\n" +
                    "4401 37\n" +
                    "4402 27\n" +
                    "9901 99\n" +
                    "9903 21\n" +
                    "9903 23\n" +
                    "9908 01";

            BufferedReader bufReader = new BufferedReader(new StringReader(code));

            String list = "";
            while ((list = bufReader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(list);
                String itemcode = st.nextToken();
                String kindcode = st.nextToken();
                String today = date;
                String formatDay = today.replaceAll("-", "");

        // Jsoup를 이용해서 크롤링
                String pageUrl = "https://www.kamis.or.kr/customer/price/livestockRetail/period.do?action=daily&itemcategorycode=500&productrankcode=0&itemcode=" +
                        itemcode + "&kindcode=" + kindcode + "&startday=" + "20230201" + "&endday=" + "20230201";

                Beef beef = new Beef();
                beef.setDate(formatDay);

                Document doc = Jsoup.connect(pageUrl).timeout(30000).get();
                Elements elements = doc.select(".clear > .s_tit6");
                Elements pElements = doc.select(".wtable3 > tbody > tr > .last");
                for (int i = 0; i < elements.size(); i++) {
                    if (i % 3 == 0) {
                        String name = elements.get(i).text();

                        StringTokenizer stringN = new StringTokenizer(name, "/");
                        String large = stringN.nextToken();
                        String medium = stringN.nextToken();
                        medium = medium.replaceAll(",", "");
                        beef.setLarge(large);
                        beef.setMedium(medium);
                    } else if (i % 3 == 1) {
                        String name = elements.get(i).text();
                        name = name.replaceAll("등급 : ", "");
                        name = name.replaceAll(",", "");
                        beef.setSmall(name);
                        System.out.println(name);

                    } else {
                        int index = i / 3;
                        if (pElements.size() <= (index * 21) + 1) {
                            continue;
                        }
                        String origin = "korea";
                        String avg = pElements.get((index * 21) + 1).text();
                        avg = avg.replaceAll(",", "");
                        String max = pElements.get((index * 21) + 2).text();
                        max = max.replaceAll(",", "");

                        String min = pElements.get((index * 21) + 3).text();
                        min = min.replaceAll(",", "");
                        if (beef.getLarge().contains("수입")) {
                            origin = "income";
                        }
                        bw.write(formatDay + "," + beef.getLarge().trim() + "," + beef.getMedium().trim() +
                                "," + beef.getSmall().trim() + "," + origin.trim() + "," + max.trim() + "," + min.trim() + "," + avg.trim());
                        bw.write(NEWLINE);
                    }
                }
            }

            bw.flush();
            bw.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
