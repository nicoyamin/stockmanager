package com.example.stockmanager.service;

import com.example.stockmanager.model.Product;
import com.example.stockmanager.model.dto.ProductDTO;
import com.example.stockmanager.model.mapper.ProductMapper;
import com.example.stockmanager.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    @Override
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    public List<ProductDTO> getAllProductsDTO() {

        List<ProductDTO> products = productMapper.toProductDTOList(getAllProducts());

        return products;
    }

}
