package com.example.soporte.config.dataInit;

import com.example.soporte.models.Product.Product;
import com.example.soporte.models.Product.Version;
import com.example.soporte.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import static java.lang.System.exit;

@Component
@Profile("default")
public class DataInitializer implements CommandLineRunner {
    @SuppressWarnings("FieldCanBeLocal")
    @Value("${products.csv.file.path}")
    private final String CSV_FILE_PATH;

    private final ProductRepository productRepository;
    private final CSVReader csvReader;

    @Autowired
    public DataInitializer(ProductRepository productRepository, CSVReader csvReader){
        this.productRepository = productRepository;
        this.csvReader = csvReader;
        this.CSV_FILE_PATH = ""; // Valor sera sobreescrito por @Value injection
    }

    /**
     * Executes on application startup. Loads products from CSV if the repository is empty.
     *
     * @param args Command-line arguments (not used).
     */
    @Override
    public void run(String... args) {
        if (productRepository.count() == 0) loadProducts();
    }

    /**
     * Loads product data from the CSV file specified in application.properties.
     * Exits the application if an error occurs during the loading process.
     */
    private void loadProducts() {
        try {
            loadProductsFromCSV();
        } catch (Exception e) {
            System.out.println("Error loading products from CSV file: " + e.getMessage() + " " + "Exiting application.");
            exit(1);
        }
    }

    /**
     * Reads products from the specified CSV file path and saves them to the database.
     * Uses the injected CSVReader to parse the CSV file.
     *
     * @throws IOException if an I/O error occurs while reading the CSV file.
     */
    private void loadProductsFromCSV() throws IOException{
        List<ProductCSV> products = csvReader.readProductsFromCSV(CSV_FILE_PATH);
        Product prod;

        for (ProductCSV product : products) {
            prod = mapProductCSVToProduct(product);
            productRepository.save(prod);
        }
    }

    /**
     * Maps a ProductCSV object to a Product entity.
     *
     * @param productCSV The ProductCSV object containing product data.
     * @return A new Product entity mapped from the ProductCSV object.
     */
    private Product mapProductCSVToProduct(ProductCSV productCSV) {
        Product product = new Product();

        product.setName(productCSV.productName);
        product.setVersions(getVersionsFromProductCSV(productCSV, product));
        return product;
    }

    /**
     * Converts version names from ProductCSV to Version entities associated with a Product.
     *
     * @param productCSV The ProductCSV object containing version names.
     * @param product    The Product entity to associate with each Version.
     * @return A list of Version entities created from version names in ProductCSV.
     */
    private List<Version> getVersionsFromProductCSV(ProductCSV productCSV, Product product) {
        List<Version> versions = new ArrayList<>();

        for (String versionName : productCSV.versionNames) {
            Version version = new Version(versionName, product);
            versions.add(version);
        }

        return versions;
    }
}