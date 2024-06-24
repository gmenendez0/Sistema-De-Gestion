package com.example.soporte.config.dataInit;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CSVReader {
    @SuppressWarnings("FieldCanBeLocal")
    private final String SEPARATOR = ";";

    @SuppressWarnings("FieldCanBeLocal")
    private final int MINIMUM_COLUMNS = 2;

    @SuppressWarnings("FieldCanBeLocal")
    private final int PRODUCT_NAME_INDEX = 0;

    /**
     * Reads product data from the specified CSV file.
     *
     * @param csvFilePath The path to the CSV file containing product data.
     * @return A list of ProductCSV objects representing products and their versions.
     * @throws IOException If an error occurs while reading the CSV file.
     */
    public List<ProductCSV> readProductsFromCSV(String csvFilePath) throws IOException {
        List<ProductCSV> products = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(SEPARATOR);

                if (data.length >= MINIMUM_COLUMNS) {
                    String productName = data[PRODUCT_NAME_INDEX];

                    List<String> versionNames = new ArrayList<>(Arrays.asList(data).subList(1, data.length));
                    ProductCSV productCSV = new ProductCSV(productName, versionNames);
                    products.add(productCSV);
                }
            }
        }

        return products;
    }
}
