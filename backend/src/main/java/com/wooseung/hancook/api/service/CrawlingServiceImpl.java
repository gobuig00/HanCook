package com.wooseung.hancook.api.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.wooseung.hancook.api.response.CrawlingPriceResponseDto;
import com.wooseung.hancook.api.response.CrawlingResponseDto;
import com.wooseung.hancook.api.response.MartResponseDto;
import com.wooseung.hancook.db.entity.Ingredient;
import com.wooseung.hancook.db.entity.Mart;
import com.wooseung.hancook.db.repository.IngredientRepository;
import com.wooseung.hancook.db.repository.MartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
    private static final String homePlus = "https://front.homeplus.co.kr/search?keyword=";
    private static final String emart = "https://emart.ssg.com/search.ssg?target=all&count=3&query=";
    private static final String lotte = "https://www.lotteon.com/search/search/search.ecn?render=search&platform=pc&mallId=4&q=";
    private final MartRepository martRepository;
    private final IngredientRepository ingredientRepository;

    @Override
    public CrawlingResponseDto craw() {

        try {

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
            List<Ingredient> ingredientList = ingredientRepository.findAll();
            for (Ingredient ingredient : ingredientList) {
                String ingreName = ingredient.getName();

                String homePlusUrl = homePlus + ingreName;
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
                    name = name.replaceAll(",", "");
                    price = price.replaceAll(",", "");
                    System.out.println(name + " " + price);
                    Mart mart = Mart.builder()
                            .ingredient(ingredient)
                            .ingreName(ingreName)
                            .itemNo(cnt)
                            .itemName(name)
                            .itemPrice(price)
                            .itemUrl("http")
                            .mart(2)
                            .build();
                    martRepository.save(mart);
                    cnt++;
                }
//이마트
                driver.get(emartUrl);
                Thread.sleep(1000);
                cnt = 1;
                elements = driver.findElements(By.cssSelector("#idProductImg > li"));
                for (WebElement element : elements) {
                    String name = element.findElement(By.cssSelector(".mnemitem_goods_tit")).getText();
                    String price = element.findElement(By.cssSelector(".ssg_price")).getText();
                    name = name.replaceAll(",", "");
                    price = price.replaceAll(",", "");

                    System.out.println(name + " " + price);
                    Mart mart = Mart.builder()
                            .ingredient(ingredient)
                            .ingreName(ingreName)
                            .itemNo(cnt)
                            .itemName(name)
                            .itemPrice(price)
                            .itemUrl("http")
                            .mart(3)
                            .build();
                    martRepository.save(mart);
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
                    name = name.replaceAll(",", "");
                    price = price.replaceAll(",", "");
                    System.out.println(name + " " + price);
                    Mart mart = Mart.builder()
                            .ingredient(ingredient)
                            .ingreName(ingreName)
                            .itemNo(cnt)
                            .itemName(name)
                            .itemPrice(price)
                            .itemUrl("http")
                            .mart(1)
                            .build();
                    martRepository.save(mart);
                    cnt++;
                }

                index++;
            }

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



