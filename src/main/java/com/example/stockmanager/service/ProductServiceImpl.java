package com.example.stockmanager.service;

import com.example.stockmanager.model.Product;
import com.example.stockmanager.model.dto.ProductDTO;
import com.example.stockmanager.model.mapper.ProductMapper;
import com.example.stockmanager.repository.ProductRepository;
import com.example.stockmanager.utils.StockUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private final StockUtils stockUtils;
    @Override
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    public List<ProductDTO> getAllProductsDTO() {

         return productMapper.toProductDTOList(getAllProducts());

    }

    public Long[] getProductsInStock() {
        List<ProductDTO> products = productMapper.toProductDTOList(getAllProducts());
        return stockUtils.identifyProductsInStock(products);
    }



}
