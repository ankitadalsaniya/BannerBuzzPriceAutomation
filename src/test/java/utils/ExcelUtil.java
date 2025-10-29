package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelUtil {
    private static final String filePath = "C:/Users/RAJ/OneDrive/Desktop/BannerBuzzPriceAutomation/testdata/Demo.xlsx";

    // Read product URLs from first column
    public static Object[][] readExcelData() throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        int rowCount = sheet.getPhysicalNumberOfRows();
        Object[][] data = new Object[rowCount - 1][1];

        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            if (row != null && row.getCell(0) != null) {
                data[i - 1][0] = row.getCell(0).getStringCellValue();
            }
        }

        workbook.close();
        fis.close();
        return data;
    }

    // Writes new price and date in NEW columns each run
    public static void writeResult(String productUrl, String price) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
        fis.close();

        // Find the row for this product URL
        int targetRowIndex = -1;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null && row.getCell(0) != null &&
                row.getCell(0).getStringCellValue().equals(productUrl)) {
                targetRowIndex = i;
                break;
            }
        }

        if (targetRowIndex == -1) {
            workbook.close();
            return; // URL not found
        }

        Row row = sheet.getRow(targetRowIndex);

        // Determine the next available column index
        int nextColIndex = row.getLastCellNum();
        if (nextColIndex < 0) nextColIndex = 1; // start after URL column

        // Generate dynamic headers
        String dateTag = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) headerRow = sheet.createRow(0);

        Cell priceHeader = headerRow.createCell(nextColIndex);
        priceHeader.setCellValue("Price_" + dateTag);

        Cell dateHeader = headerRow.createCell(nextColIndex + 1);
        dateHeader.setCellValue("Date_" + dateTag);

        // Write price and timestamp
        Cell priceCell = row.createCell(nextColIndex);
        priceCell.setCellValue(price);

        Cell dateCell = row.createCell(nextColIndex + 1);
        String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        dateCell.setCellValue(timestamp);

        // Save Excel file
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        workbook.close();
        fos.close();
    }
}
