import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TestYandex {
    public static void main(String[] args) {

        int dur = 4;
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(options);
        Actions actions = new Actions(driver);

        try {

//          WebDriver в папке проекта
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
            driver.get("https://yandex.ru/");

//          Переход в маркет
            WebElement element_market = new WebDriverWait(driver, Duration.ofSeconds(dur))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@data-statlog='services_new.item.market.0']")));
            String URL_market = element_market.getAttribute("href");
            String[] split_URL_market = URL_market.split("=");
            driver.get(split_URL_market[0]);

//          Принять куки
            WebElement element_acceptCookie = new WebDriverWait(driver, Duration.ofSeconds(dur))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@data-id=\"table\"]//button[@data-text=\"Принять все\"]")));
            element_acceptCookie.click();

//          Переход в электронику
            WebElement element_menu = new WebDriverWait(driver, Duration.ofSeconds(dur))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-zone-name='category-link']//a[@href='/catalog--elektronika/54440']")));
            String URL_menu = element_menu.getAttribute("href");
            driver.get(URL_menu);

//          Переход в телефоны
            WebElement element_phones = new WebDriverWait(driver, Duration.ofSeconds(dur))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-zone-name='link']//a[text()='Смартфоны']")));
            String URL_phones = element_phones.getAttribute("href");
            driver.get(URL_phones);

//          Переход в расширенный поиск
            WebElement element_filters = new WebDriverWait(driver, Duration.ofSeconds(dur))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='_3RoU0']//a[@data-auto='allFiltersButton']")));
            String URL_filters = element_filters.getAttribute("href");
            driver.get(URL_filters);

//          Фильтр до 20к₽
            WebElement element_price = new WebDriverWait(driver, Duration.ofSeconds(dur))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-prefix=\"до\"]//input")));
            element_price.sendKeys("20000");

//          5 checkboxes в расширенном поиске
            actions.sendKeys(Keys.PAGE_DOWN).perform();
            Thread.sleep(dur * 1000);
            List<WebElement> element_checkboxes = driver.findElements(By.xpath("//div[@data-filter-id=\"7893318\"]//label[@class=\"cyT3Q\"]//div[@class=\"_1WMsA _1VtMI _176_6\"]"));
            for (int i = 0; i < 5; i++) {
                element_checkboxes.get(i).click();
            }

//          5 дюймов
            WebElement element_screenSize = new WebDriverWait(driver, Duration.ofSeconds(dur))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class=\"_3i0uk\"]//h4[contains(text(),'Диагональ экрана (точно)')]")));
            element_screenSize.click();
            List<WebElement> element_inputs = driver.findElements(By.xpath("//div[@class=\"_3i0uk\"]//div[@data-prefix=\"от\"]//input[@class=\"_2xtC2\"]"));
            element_inputs.get(1).sendKeys("5");

//          Отображение результата сортировки
            WebElement element_searchResult = new WebDriverWait(driver, Duration.ofSeconds(dur))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'Показать')]")));
            String URL_searchResult = element_searchResult.getAttribute("href");
            driver.get(URL_searchResult);

//          Первый элемент в списке
            WebElement element_firstResult = new WebDriverWait(driver, Duration.ofSeconds(dur))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-index=\"1\"]//a")));
            String URL_firstResult = element_firstResult.getAttribute("href");
            Thread.sleep(dur * 1000);

//          Количество выведенных результатов
            List<WebElement> element_fullResults = driver.findElements(By.xpath("//div[@style=\"overflow-anchor: none;\"]"));
            System.out.println("Отображено " + (element_fullResults.size()) + " результатов");

//          Смена сортировки на цену
            WebElement element_sortByPrice = new WebDriverWait(driver, Duration.ofSeconds(dur))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='по цене']")));
            element_sortByPrice.click();
            Thread.sleep(dur * 1000);

//          Переход по первому элементу в списке
            driver.get(URL_firstResult);

//          Вывод оценки первого элемента
            WebElement element_rating = new WebDriverWait(driver, Duration.ofSeconds(dur))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='_1EOgH _2I6wc _1NfPD']//span")));
            String rating = element_rating.getAttribute("innerText");
            System.out.println("Рейтинг первого элемента " + rating);
            Thread.sleep(dur * 1000);

        } catch (InterruptedException e) {
            e.printStackTrace();

        } finally {
//          Закрытие сессии
            driver.quit();
        }
    }
}
