package com.example.soporte.config.dataInit;//package com.example.soporte.config.dataInit;
//
//import com.example.soporte.models.Product.Product;
//import com.example.soporte.models.Product.Version;
//import com.example.soporte.repositories.ProductRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.ArrayList;
//
//import static java.lang.System.exit;
//
//@Component
//@Profile("default")
//public class DataInitializer implements CommandLineRunner {
//    @SuppressWarnings("FieldCanBeLocal")
//    @Value("${products.csv.file.path}")
//    private final String CSV_FILE_PATH;
//
//    private final ProductRepository productRepository;
//    private final CSVReader csvReader;
//
//    @Autowired
//    public DataInitializer(ProductRepository productRepository, CSVReader csvReader){
//        this.productRepository = productRepository;
//        this.csvReader = csvReader;
//        this.CSV_FILE_PATH = ""; // Valor sera sobreescrito por @Value injection
//    }
//
//    /**
//     * Executes on application startup. Loads products from CSV if the repository is empty.
//     *
//     * @param args Command-line arguments (not used).
//     */
//    @Override
//    public void run(String... args) {
//        if (productRepository.count() == 0) loadProducts();
//    }
//
//    /**
//     * Loads product data from the CSV file specified in application.properties.
//     * Exits the application if an error occurs during the loading process.
//     */
//    private void loadProducts() {
//        try {
//            System.out.println("Current working directory: " + System.getProperty("user.dir"));
//            loadProductsFromCSV();
//
//        } catch (Exception e) {
//            System.out.println("Error loading products from CSV file: " + e.getMessage() + " " + "Exiting application.");
//            exit(1);
//        }
//    }
//
//    /**
//     * Reads products from the specified CSV file path and saves them to the database.
//     * Uses the injected CSVReader to parse the CSV file.
//     *
//     * @throws IOException if an I/O error occurs while reading the CSV file.
//     */
//    private void loadProductsFromCSV() throws IOException{
//        List<ProductCSV> products = csvReader.readProductsFromCSV(CSV_FILE_PATH);
//        Product prod;
//
//        for (ProductCSV product : products) {
//            prod = mapProductCSVToProduct(product);
//            productRepository.save(prod);
//        }
//    }
//
//    /**
//     * Maps a ProductCSV object to a Product entity.
//     *
//     * @param productCSV The ProductCSV object containing product data.
//     * @return A new Product entity mapped from the ProductCSV object.
//     */
//    private Product mapProductCSVToProduct(ProductCSV productCSV) {
//        Product product = new Product();
//
//        product.setName(productCSV.productName);
//        product.setVersions(getVersionsFromProductCSV(productCSV, product));
//        return product;
//    }
//
//    /**
//     * Converts version names from ProductCSV to Version entities associated with a Product.
//     *
//     * @param productCSV The ProductCSV object containing version names.
//     * @param product    The Product entity to associate with each Version.
//     * @return A list of Version entities created from version names in ProductCSV.
//     */
//    private List<Version> getVersionsFromProductCSV(ProductCSV productCSV, Product product) {
//        List<Version> versions = new ArrayList<>();
//
//        for (String versionName : productCSV.versionNames) {
//            Version version = new Version(versionName, product);
//            versions.add(version);
//        }
//
//        return versions;
//    }
//}@Component
//@Profile("default")
//public class DataInitializer implements CommandLineRunner {
//
//    @Autowired
//    private ProductRepository productRepository;
//    @Override
//    public void run(String... args) throws Exception {
//        if (productRepository.count() == 0) {
//            // TODO cambiar por YAML
//            Product productA = new Product("SIU");
//
//            Version versionA1 = new Version("2.0",productA);
//
//            Version versionA2 = new Version("1.1", productA);
//
//            productA.addVersion(versionA1);
//            productA.addVersion(versionA2);
//            Product productB = new Product("VSFS");
//
//            Version versionB1 = new Version("2.0",productB);
//
//            Version versionB2 = new Version("1.1", productB);
//            productB.addVersion(versionB1);
//            productB.addVersion(versionB2);
//            productRepository.saveAll(List.of(productA, productB ));
//        }
//    }


import com.example.soporte.models.Product.Product;
import com.example.soporte.models.Product.Version;
import com.example.soporte.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Profile("default")
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            // TODO cambiar por YAML
            createAndSaveProduct("Enterprise Resource Planner", List.of("2.0.9", "1.2.8"));
            createAndSaveProduct("Amazon Web Service", List.of("2.0.9", "2.1.0"));
            createAndSaveProduct("Secure Cloud Service", List.of("2.0.9", "1.8.6"));
            createAndSaveProduct("Advanced Analytics Suite ", List.of("2.0.4", "1.1.8", "1.1.9"));
            createAndSaveProduct("Intelligent Automation Tool ", List.of("2.0.4", "1.5.8", "1.1.9"));
        }
    }
    private void createAndSaveProduct(String productName, List<String> versionNumbers) {
        Product product = new Product(productName);

        for (String versionNumber : versionNumbers) {
            Version version = new Version(versionNumber, product);
            product.addVersion(version);
        }

        productRepository.save(product);
    }
}