package com.hancook.multicrawler;

import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
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

            System.setProperty("webdriver.chrome.driver", "./driver/chromedriver");
/*            ChromeOptions options = new ChromeOptions();
//          브라우저 내부적으로 실행

            options.setBinary("/usr/bin/google-chrome");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-ipv6");

            // User-Agent 변경
            options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36");

            // 프록시 설정
            Proxy proxy = new Proxy();
            proxy.setHttpProxy("myproxy:80");
            proxy.setSslProxy("myproxy:80");
            options.setCapability("proxy", proxy);

//            WebDriver driver = new ChromeDriver(options);
            ChromeDriverService service = new ChromeDriverService.Builder()
                    .usingAnyFreePort()
                    .build();*/

            ChromeOptions options = new ChromeOptions();
            options.setBinary("/usr/bin/google-chrome");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-ipv6");
            options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.5563.64 Safari/537.36");
            options.addArguments("--whitelisted-ips");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--allowed-ips=127.0.0.1");
//            options.addArguments("--remote-debugging-port=9222");
            options.addArguments("--remote-debugging-address=127.0.0.1");

// 프록시 설정
//            Proxy proxy = new Proxy();
//            proxy.setHttpProxy("80:80");
//            options.setCapability("proxy", proxy);

// 웹 드라이버 생성
            ChromeDriverService service = new ChromeDriverService.Builder()
                    .usingPort(9222) // 여기서 원하는 포트 번호를 설정합니다.
                    .usingDriverExecutable(new File("./driver/chromedriver"))
                    .build();

            WebDriver driver = new ChromeDriver(service, options);
//            System.out.println("WebDriver Version: " + dr

            File file = new File("./driver/ingre.txt");
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
                    Thread.sleep(10000);

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
                        System.out.println("이우승 화이팅");
                        cnt++;
                    }
//이마트
                    driver.get(emartUrl);
                    Thread.sleep(10000);
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
                    Thread.sleep(10000);
                    elements = driver.findElements(By.cssSelector("#c301_goods > ul > li"));
                    System.out.println(elements.size());
                    cnt = 1;
                    for (WebElement element : elements) {
                        if (cnt == 4) {
                            break;
                        }
//                        String name = element.findElement(By.cssSelector(".srchProductUnitTitle")).getText();
//                        String price = element.findElement(By.cssSelector(".s-product-price__final > .s-product-price__number")).getText();
                        String name = element.findElement(By.xpath(".//span[@class='srchProductUnitTitle']")).getText();
                        String price = element.findElement(By.xpath(".//span[@class='s-product-price__final']/span[@class='s-product-price__number']")).getText();
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
