package com.example.stockmanager.controller;

import com.example.stockmanager.model.dto.ProductDTO;
import com.example.stockmanager.service.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {

        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    //This is the call to trigger the algorithm
    @GetMapping("/inStock")
    public ResponseEntity<Long[]> getProductsInStock() {

        return new ResponseEntity<>(productService.getProductsInStock(), HttpStatus.OK);
    }
}