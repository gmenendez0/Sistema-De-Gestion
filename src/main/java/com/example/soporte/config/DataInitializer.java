package com.example.soporte.config;

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
            Product productA = new Product("SIU");

            Version versionA1 = new Version("2.0",productA);

            Version versionA2 = new Version("1.1", productA);

            productA.addVersion(versionA1);
            productA.addVersion(versionA2);
            Product productB = new Product("VSFS");

            Version versionB1 = new Version("2.0",productB);

            Version versionB2 = new Version("1.1", productB);
            productB.addVersion(versionB1);
            productB.addVersion(versionB2);
            productRepository.saveAll(List.of(productA, productB ));
        }
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}