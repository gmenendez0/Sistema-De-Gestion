package com.example.soporte.controllers;

import com.example.soporte.models.Product.Product;
import com.example.soporte.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;

// ? necesitan un endpoint que de todas las versiones de un producto?
// ? Necesitan un endpoint que de un product X en particular?
// ? Necesitan un endpoint que devuelva todas las versiones de todos los productos? SI
// ? Necesitan un endpoint que devuelva todos los tickets de una version X
// ? Necesitan un endpoint que de todos los productos? NO
// ? Endpoint que devuelva el array de ids de tareas asociados a un ticket X


@RestController
@RequestMapping("/v1/products")
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