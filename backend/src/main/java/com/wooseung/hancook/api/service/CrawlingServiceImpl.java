package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.CrawlingPriceResponseDto;
import com.wooseung.hancook.api.response.CrawlingResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Service("crawlingService")
@RequiredArgsConstructor
@Slf4j
public class CrawlingServiceImpl implements CrawlingService {

    private WebDriver driver;
    private static final String hompPlus = "https://front.homeplus.co.kr/search?keyword=";
    private static final String emart = "https://emart.ssg.com/search.ssg?sort=best&count=3&query=";
    private static final String lotte = "https://www.lotteon.com/search/search/search.ecn?render=search&platform=pc&mallId=4&q=";

    @Override
    public CrawlingResponseDto craw(String keyword) {
        File file = new File("C:\\\\Users\\\\SSAFY\\\\Downloads\\\\ingre.txt");
        FileReader fileReader = null;

        // 파일 입력
        String filepath = "C:\\\\Users\\\\SSAFY\\\\Downloads\\\\ingre_csv.csv";
        File wFile = new File(filepath);

        String NEWLINE = System.lineSeparator();

        try {
            fileReader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(fileReader);

            //파일 입력
            BufferedWriter bw = new BufferedWriter(new FileWriter(wFile));
            bw.write("번호,재료명,아이템명,가격,아이템번호,마트번호");
            bw.write(NEWLINE);

            String ingreName = null;
            System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\crawling\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();

//        //브라우저 내부적으로 실행
            options.addArguments("headless");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-gpu");            //gpu 비활성화
            options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받음
            options.addArguments("--disable-popup-blocking");       //팝업안띄움
            WebDriver driver = new ChromeDriver(options);

            int index = 1;

            while ((ingreName = bufReader.readLine()) != null) {
                List<CrawlingPriceResponseDto> homeplusList = new ArrayList<>();
                List<CrawlingPriceResponseDto> emartList = new ArrayList<>();
                List<CrawlingPriceResponseDto> lotteList = new ArrayList<>();
                String homePlusUrl = hompPlus + ingreName;
                String emartUrl = emart + ingreName;
                String lotteUrl = lotte + ingreName;

                driver.get(homePlusUrl);
                Thread.sleep(1000);

                List<WebElement> elements = driver.findElements(By.cssSelector(".itemDisplayList > div > .detailInfo"));
                System.out.println(elements.size());
                int cnt = 1;
                for (WebElement element : elements) {
                    if (cnt == 4) {
                        break;
                    }
                    String name = element.findElement(By.cssSelector("a >.css-12cdo53-defaultStyle-Typography-ellips")).getText();
                    String price = element.findElement(By.cssSelector("div > div > .priceValue")).getText();
                    homeplusList.add(new CrawlingPriceResponseDto(name, price));
                    name = name.replaceAll(",","");
                    price =price.replaceAll(",", "");
                    System.out.println(name + " " + price);
                    bw.write(index+","+ingreName+","+name+","+price+","+Integer.toString(cnt)+","+"1");
                    bw.write(NEWLINE);
                    cnt++;
                }

                driver.get(emartUrl);
                Thread.sleep(1000);
                cnt=1;
                elements = driver.findElements(By.cssSelector("#idProductImg > li"));
                for (WebElement element : elements) {
                    String name = element.findElement(By.cssSelector(".mnemitem_goods_tit")).getText();
                    String price = element.findElement(By.cssSelector(".ssg_price")).getText();
                    emartList.add(new CrawlingPriceResponseDto(name, price));
                    name = name.replaceAll(",","");                    price =price.replaceAll(",", "");
                    bw.write(index+","+ingreName+","+name+","+price+","+Integer.toString(cnt)+","+"2");
                    bw.write(NEWLINE);
                    System.out.println(name + " " + price);
                    cnt++;
                }

                driver.get(lotteUrl);
                Thread.sleep(1000);
                elements = driver.findElements(By.cssSelector("#c301_goods > ul > li"));
                System.out.println(elements.size());
                cnt = 1;
                for (WebElement element : elements) {
                    if (cnt == 4) {
                        break;
                    }
                    String name = element.findElement(By.cssSelector(".srchProductUnitTitle")).getText();
                    String price = element.findElement(By.cssSelector(".s-product-price__final > .s-product-price__number")).getText();
                    lotteList.add(new CrawlingPriceResponseDto(name, price));
                    name = name.replaceAll(",","");
                    price =price.replaceAll(",", "");
                    System.out.println(name + " " + price);
                    bw.write(index+","+ingreName+","+name+","+price+","+Integer.toString(cnt)+","+"3");
                    bw.write(NEWLINE);
                    cnt++;
                }

                index++;
            }
            bw.flush();
            bw.close();
            System.out.println(index);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

//        try {
//            //드라이버가 null이 아니라면
//            if (driver != null) {
//                //드라이버 연결 종료
//                driver.close(); //드라이버 연결 해제
//
//                //프로세스 종료
//                driver.quit();
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }



