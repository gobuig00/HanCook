package com.hancook.dealcrawler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.*;
import java.util.List;
import java.util.StringTokenizer;

public class MartCrawler implements Runnable {
    private WebDriver driver;
    private static final String homePlus = "https://front.homeplus.co.kr/search?keyword=";
    private static final String emart = "https://emart.ssg.com/search.ssg?target=all&count=3&query=";
    private static final String lotte = "https://www.lotteon.com/search/search/search.ecn?render=search&platform=pc&mallId=4&q=";

    @Override
    public void run() {
        init();
    }

    private void init() {
        try {

            System.setProperty("webdriver.chrome.driver", "src/main/resources/crawling/chromedriver.exe");
            ChromeOptions options = new ChromeOptions();

//        //브라우저 내부적으로 실행
            options.addArguments("headless");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-gpu");            //gpu 비활성화
            options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받음
            options.addArguments("--disable-popup-blocking");       //팝업안띄움
            WebDriver driver = new ChromeDriver(options);

            File file = new File("src/main/resources/crawling/ingre.txt");
            FileReader fileReader = null;
            fileReader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(fileReader);


            String wfilepath = "./data/mart/ingre.csv";
            File wFile = new File(wfilepath);
            String NEWLINE = System.lineSeparator();
            BufferedWriter bw = new BufferedWriter(new FileWriter(wFile));
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufReader = new BufferedReader(fileReader);
            int index = 1;
            String ingreName = null;
            while ((ingreName = bufReader.readLine()) != null) {
                    StringTokenizer sToken = new StringTokenizer(ingreName,",");
                    ingreName=sToken.nextToken();
                    String ingreId = sToken.nextToken();
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
                        bw.write(ingreName+","+name+","+cnt+","+price+","+"no"+","+"2"+","+ingreId);
                        bw.write(NEWLINE);

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
                        bw.write(ingreName+","+name+","+cnt+","+price+","+"no"+","+"3"+","+ingreId);
                        bw.write(NEWLINE);
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

                        bw.write(ingreName+","+name+","+cnt+","+price+","+"no"+","+"1"+","+ingreId);
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
    }

}
