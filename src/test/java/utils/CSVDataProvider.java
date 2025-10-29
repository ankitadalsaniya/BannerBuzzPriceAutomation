package utils;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import com.opencsv.exceptions.CsvException;

public class CSVDataProvider {

    private static final String CSV_FILE = "testdata/ProductURLs.csv";

    // Add CsvException here ↓↓↓
    public static List<String[]> readData() throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE))) {
            return reader.readAll();
        }
    }

    public static void writeData(List<String[]> data) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(CSV_FILE))) {
            writer.writeAll(data);
        }
    }
}
