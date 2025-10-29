package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import utils.ExcelUtil;

import java.io.IOException;
import java.time.Duration;

public class BannerBuzzPriceTest {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @DataProvider(name = "productData")
    public Object[][] getProductData() throws IOException {
        return ExcelUtil.readExcelData();
    }

    @Test(dataProvider = "productData")
    public void fetchProductPrice(String productUrl) throws IOException {
        driver.get(productUrl);

        // Update this locator to match actual price element on your product page
        String price = driver.findElement(By.cssSelector(".NewPriceBox span:nth-of-type(2)")).getText();
        System.out.println("Fetched price for " + productUrl + ": " + price);

        ExcelUtil.writeResult(productUrl, price);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
