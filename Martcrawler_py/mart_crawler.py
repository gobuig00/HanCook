from selenium import webdriver
from selenium.webdriver.common.by import By
import csv
import time


class MartCrawler:
    homePlus = "https://front.homeplus.co.kr/search?keyword="
    emart = "https://emart.ssg.com/search.ssg?target=all&count=3&query="
    lotte = "https://www.lotteon.com/search/search/search.ecn?render=search&platform=pc&mallId=4&q="

    def run(self):
        self.init()

    def init(self):
        try:
            options = webdriver.ChromeOptions()
            options.add_argument("headless")
            options.add_argument("--remote-allow-origins=*")
            options.add_argument("--no-sandbox")
            options.add_argument("disable-dev-shm-usage")
            options.add_argument("--disable-gpu")
            options.add_argument("--blink-settings=imagesEnabled=false")
            options.add_argument("--disable-popup-blocking")
            driver = webdriver.Chrome(
                "/usr/local/bin/chromedriver", options=options)

            with open("/home/ingre.txt", "r", encoding="utf-8") as file:
                bufReader = csv.reader(file)
                with open("/home/data/mart/mart.csv", "w", newline="", encoding="utf-8") as wFile:
                    writer = csv.writer(wFile)
                    index = 1
                    for line in bufReader:
                        ingreName, ingreId = line
                        homePlusUrl = self.homePlus + ingreName
                        emartUrl = self.emart + ingreName
                        lotteUrl = self.lotte + ingreName

                        driver.get(homePlusUrl)
                        time.sleep(1)

                        elements = driver.find_elements(
                            By.CSS_SELECTOR, ".itemDisplayList > div > .detailInfo")
                        cnt = 1
                        for element in elements:
                            if cnt == 4:
                                break
                            name = element.find_element(
                                By.CSS_SELECTOR, "a >.css-12cdo53-defaultStyle-Typography-ellips").text
                            price = element.find_element(
                                By.CSS_SELECTOR, "div > div > .priceValue").text
                            name = name.replace(",", "")
                            price = price.replace(",", "")
                            writer.writerow(
                                [ingreName, name, cnt, price, "no", "2", ingreId])
                            cnt += 1

                        driver.get(emartUrl)
                        time.sleep(1)
                        cnt = 1
                        elements = driver.find_elements(
                            By.CSS_SELECTOR, "#idProductImg > li")
                        for element in elements:
                            name = element.find_element(
                                By.CSS_SELECTOR, ".mnemitem_goods_tit").text
                            price = element.find_element(
                                By.CSS_SELECTOR, ".ssg_price").text
                            name = name.replace(",", "")
                            price = price.replace(",", "")
                            writer.writerow(
                                [ingreName, name, cnt, price, "no", "3", ingreId])
                            cnt += 1

                        driver.get(lotteUrl)
                        time.sleep(1)
                        elements = driver.find_elements(
                            By.CSS_SELECTOR, "#c301_goods > ul > li")
                        cnt = 1
                        for element in elements:
                            if cnt == 4:
                                break
                            name = element.find_element(
                                By.CSS_SELECTOR, ".srchProductUnitTitle").text
                            price = element.find_element(
                                By.CSS_SELECTOR, ".s-product-price__final > .s-product-price__number").text
                            name = name.replace(",", "")
                            price = price.replace(",", "")
                            writer.writerow(
                                [ingreName, name, cnt, price, "no", "1", ingreId])
                            cnt += 1

                        index += 1

            print(index)
            driver.close()

        except Exception as e:
            print(e)
            driver.close()


def main():
    crawler = MartCrawler()
    crawler.run()


if __name__ == "__main__":
    main()
