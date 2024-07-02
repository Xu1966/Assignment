package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageService.HomePage;
import pageService.SearchPage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HamroBazaarTest {
    WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\sujanmanandhar\\Desktop\\SeleniumTask\\Verisk automation Task\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
        driver.get("https://hamrobazaar.com/");
    }

    @Test
    public void test() throws IOException {
        HomePage homeObj = new HomePage(driver);
        homeObj.enterSearchText("Monitor");
        SearchPage searchObj = homeObj.clickSearchButton();
        searchObj.enterLocation("New Road");
        searchObj.selectLocation();
        searchObj.slideRange();
        searchObj.filter();
        searchObj.sortDropdown();
        List<WebElement> productTitles = searchObj.getProductTitleList();
        List<WebElement> productDescriptions = searchObj.getProductDescriptionList();
        List<WebElement> productPrices = searchObj.getProductPriceList();
        List<WebElement> productConditions = searchObj.getProductConditionList();
        List<WebElement> productAdPostedDates = searchObj.getProductAdPostedDateList();
        List<WebElement> productSellerName = searchObj.getProductSellerNameList();
        WebDriverWait wait = new WebDriverWait(driver,5);
        writeDataToCSV(productTitles, productDescriptions, productPrices, productConditions, productAdPostedDates, productSellerName);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    private static void writeDataToCSV(List<WebElement> names, List<WebElement> descriptions, List<WebElement> prices,
                                       List<WebElement> condition,List<WebElement> adPostedDate,
                                       List<WebElement> productSeller) throws IOException {
        FileWriter csvWriter = new FileWriter("C:\\Users\\sujanmanandhar\\Desktop\\SeleniumTask\\Verisk automation Task\\output.csv");
        csvWriter.append("Name,Description,Price,Condition,AdPostedDate,SellerName\n");
        for (int i = 0; i < names.size(); i++) {
            csvWriter.append(names.get(i).getText())
                    .append(",")
                    .append(descriptions.get(i).getText())
                    .append(",")
                    .append(prices.get(i).getText())
                    .append(",")
                    .append(condition.get(i).getText())
                    .append(",")
                    .append(adPostedDate.get(i).getText())
                    .append(",")
                    .append(productSeller.get(i).getText())
                    .append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }
}

