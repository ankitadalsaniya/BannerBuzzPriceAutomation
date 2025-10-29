package tests;

import base.BaseClass;
import utils.CSVDataProvider;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.util.List;

public class ProductPriceTest extends BaseClass {

    @Test
    public void extractProductPrices() throws IOException, InterruptedException, CsvException {
        setup();

        List<String[]> data = CSVDataProvider.readData();

        for (int i = 1; i < data.size(); i++) {
            String url = data.get(i)[0];
            driver.get(url);
            Thread.sleep(3000); // small delay for page load

            String price = "";
            try {
                price = driver.findElement(By.cssSelector(".NewPriceBox span:nth-of-type(2)")).getText();
            } catch (Exception e) {
                price = "Price not found";
            }

            data.get(i)[1] = price; // write price in second column
            System.out.println("URL: " + url + " | Price: " + price);
        }

        CSVDataProvider.writeData(data);
        tearDown();
    }
}
