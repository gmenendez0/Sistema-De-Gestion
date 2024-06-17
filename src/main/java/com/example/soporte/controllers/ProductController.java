package com.example.soporte.controllers;


import com.example.soporte.models.Product.Product;
import com.example.soporte.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;

@RestController
@RequestMapping("/products")
public class ProductController extends Controller{

    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> getProducts(){
        try {
            Collection<Product> products = productService.getProducts();
            return okResponse(products);
        } catch (Exception e) {
            return handleError(e);
        }
    }
}