package com.example.stockmanager.service;

import com.example.stockmanager.model.Product;
import com.example.stockmanager.model.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();

    Long[] getProductsInStock();

}
