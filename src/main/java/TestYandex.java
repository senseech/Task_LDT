import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TestYandex {
    public static void main(String[] args) throws InterruptedException {

        int dur = 2;
        WebDriver driver = new ChromeDriver();

        try {
            //WebDriver в папке проекта
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
            driver.get("https://yandex.ru/");

            //переход в маркет
            WebElement element_market = new WebDriverWait(driver, Duration.ofSeconds(dur))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@data-statlog='services_new.item.market.0']")));
            String URL_market = element_market.getAttribute("href");
            String[] split_URL_market = URL_market.split("=");
            driver.get(split_URL_market[0]);

            //переход в электронику
            WebElement element_menu = new WebDriverWait(driver, Duration.ofSeconds(dur))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-zone-name='category-link']//a[@href='/catalog--elektronika/54440']")));
            String URL_menu = element_menu.getAttribute("href");
            driver.get(URL_menu);

            //переход в телефоны
            WebElement element_phones = new WebDriverWait(driver, Duration.ofSeconds(dur))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-zone-name='link']//a[text()='Смартфоны']")));
            String URL_phones = element_phones.getAttribute("href");
            driver.get(URL_phones);

            //5 checkboxes
            List<WebElement> element_checkboxes = driver.findElements(By.xpath("//div[@class='e4ez9']//label[@class='_3YMut _2wsVV _3qPzI ymgpk eJg5j']"));
            for (int i = 0; i < 5; i++) {
                element_checkboxes.get(i).click();
            }

            //переход в расширенный поиск
            WebElement element_filters = new WebDriverWait(driver, Duration.ofSeconds(dur))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='_3RoU0']//a[@data-auto='allFiltersButton']")));
            String URL_filters = element_filters.getAttribute("href");
            driver.get(URL_filters);

            //фильтр до 20к р
            WebElement element_price = new WebDriverWait(driver, Duration.ofSeconds(dur))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-prefix=\"до\"]//input")));
            element_price.sendKeys("20000");

            //отображение результата сортировки
            Thread.sleep(3000);
            WebElement element_searchResult = new WebDriverWait(driver, Duration.ofSeconds(dur))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'Показать')]")));
            String countResult = element_searchResult.getAttribute("text");
            String[] splitResult = countResult.split(" ");
            System.out.println("Найдено " + splitResult[1] + " подходящих результатов");
            element_searchResult.click();

            //первый элемент в списке
            WebElement element_firstResult = new WebDriverWait(driver, Duration.ofSeconds(dur))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-index=\"1\"]//a")));
            String URL_firstResult = element_firstResult.getAttribute("href");

            //смена сортировки
            WebElement element_sortByPrice = new WebDriverWait(driver, Duration.ofSeconds(dur))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='по цене']")));
            element_sortByPrice.click();
            Thread.sleep(2000);

            //переходим по первому элементу в списке
            driver.get(URL_firstResult);

            //вывод оценки первого элемента
            WebElement element_rating = new WebDriverWait(driver, Duration.ofSeconds(dur))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='_1EOgH _2I6wc _1NfPD']//span")));
            String rating = element_rating.getAttribute("innerText");
            System.out.println("Рейтинг первого элемента " + rating);
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();

        } finally {
            //закрытие сессии
            driver.quit();
        }

    }
}
