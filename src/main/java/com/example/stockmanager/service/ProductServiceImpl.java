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
    public List<ProductDTO> getAllProducts() {

         return productMapper.toProductDTOList(productRepository.findAll());

    }

    @Override
    public Long[] getProductsInStock() {
        List<ProductDTO> products = getAllProducts();
        return stockUtils.identifyProductsInStock(products);
    }



}
