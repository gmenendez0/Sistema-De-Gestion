package com.example.soporte.config.dataInit;

import java.util.List;

public class ProductCSV{
    public String productName;
    public List<String> versionNames;

    public ProductCSV(String productName, List<String> versionNames){
        this.productName = productName;
        this.versionNames = versionNames;
    }
}
