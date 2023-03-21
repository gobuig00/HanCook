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
        try {
            fileReader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(fileReader);
            String ingreName = null;
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\SSAFY\\Downloads\\chromedriver_win32\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();

//        //브라우저 내부적으로 실행
            options.addArguments("headless");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-gpu");            //gpu 비활성화
            options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받음
            options.addArguments("--disable-popup-blocking");       //팝업안띄움
            WebDriver driver = new ChromeDriver(options);

            int index = 0;

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
                int cnt = 0;
                for (WebElement element : elements) {
                    if (cnt == 3) {
                        break;
                    }
                    String name = element.findElement(By.cssSelector("a >.css-12cdo53-defaultStyle-Typography-ellips")).getText();
                    String price = element.findElement(By.cssSelector("div > div > .priceValue")).getText();
                    homeplusList.add(new CrawlingPriceResponseDto(name, price));
                    System.out.println(name + " " + price);
                    cnt++;
                }
                index++;
            }
            System.out.println(index);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
//        String homePlusUrl = hompPlus + keyword;
//        String emartUrl = emart + keyword;
//        String lotteUrl = lotte + keyword;
//        System.out.println(homePlusUrl);
//        List<MartPriceResponseDto> homeplusList = new ArrayList<>();
//        List<MartPriceResponseDto> emartList = new ArrayList<>();
//        List<MartPriceResponseDto> lotteList = new ArrayList<>();
//
//        try {
//            System.setProperty("webdriver.chrome.driver", "C:\\Users\\SSAFY\\Downloads\\chromedriver_win32\\chromedriver.exe");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //크롬 설정을 담은 객체 생성
//        ChromeOptions options = new ChromeOptions();
//
//        //브라우저 내부적으로 실행
//        options.addArguments("headless");
//        options.addArguments("--remote-allow-origins=*");
//        options.addArguments("--disable-gpu");			//gpu 비활성화
//        options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받음
//        options.addArguments("--disable-popup-blocking");       //팝업안띄움
//        WebDriver driver = new ChromeDriver(options);
//
//        //WebDriver을 해당 url로 이동한다.
//        driver.get(homePlusUrl);
//        try {
//            Thread.sleep(1000);
//            List<WebElement> elements = driver.findElements(By.cssSelector(".itemDisplayList > div > .detailInfo"));
//            System.out.println(elements.size());
//            int cnt=0;
//            for (WebElement element : elements) {
//                if(cnt==3){
//                    break;
//                }
//                String name = element.findElement(By.cssSelector("a >.css-12cdo53-defaultStyle-Typography-ellips")).getText();
//                String price = element.findElement(By.cssSelector("div > div > .priceValue")).getText();
//                homeplusList.add(new MartPriceResponseDto(name,price));
//                System.out.println(name + " " + price);
//                cnt++;
//            }
//        } catch (InterruptedException e) {
//        }
//        System.out.println("이마트");
//        driver.get(emartUrl);
//        try {
//            Thread.sleep(1000);
//            List<WebElement> elements = driver.findElements(By.cssSelector("#idProductImg > li"));
//            for (WebElement element : elements) {
//                String name=element.findElement(By.cssSelector(".mnemitem_goods_tit")).getText();
//                String price = element.findElement(By.cssSelector(".ssg_price")).getText();
//                emartList.add(new MartPriceResponseDto(name,price));
//                System.out.println(name + " " + price);
//
//            }
//
//
//        } catch (InterruptedException e) {
//        }
//        System.out.println("롯데마트");
//        driver.get(lotteUrl);
//        try {
//            Thread.sleep(1000);
//            List<WebElement> elements = driver.findElements(By.cssSelector("#c301_goods > ul > li"));
//            System.out.println(elements.size());
//            int cnt =0;
//            for (WebElement element : elements) {
//                if(cnt==3){
//                    break;
//                }
//                String name=element.findElement(By.cssSelector(".srchProductUnitTitle")).getText();
//                String price = element.findElement(By.cssSelector(".s-product-price__final > .s-product-price__number")).getText();
//                lotteList.add(new MartPriceResponseDto(name,price));
//                System.out.println(name + " " + price);
//
//                cnt++;
//
//            }
//
//
//        } catch (InterruptedException e) {
//        }
//        MartResponseDto mart = new MartResponseDto(homeplusList,emartList,lotteList);
//        return mart;
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



