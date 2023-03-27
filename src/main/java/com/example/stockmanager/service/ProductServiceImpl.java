package com.example.stockmanager.service;

import com.example.stockmanager.model.Product;
import com.example.stockmanager.model.dto.ProductDTO;
import com.example.stockmanager.model.dto.SizeDTO;
import com.example.stockmanager.model.mapper.ProductMapper;
import com.example.stockmanager.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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

         return productMapper.toProductDTOList(getAllProducts());

    }

    public Long[] identifyProductsInStock() {
        List<ProductDTO> products = productMapper.toProductDTOList(getAllProducts());

        List<ProductDTO> filteredProducts = new ArrayList<>();
        for(ProductDTO product : products) {
            boolean regularInStock = false;
            boolean containsSpecialSize = false;
            boolean specialInStock = false;
            for(SizeDTO size : product.getSizesDTO()) {
                if(size.getSpecial()) {
                    containsSpecialSize = true;
                    specialInStock = checkStock(size) || specialInStock;
                } else {
                    regularInStock = checkStock(size) || regularInStock;
                }
            }
            if(!containsSpecialSize && regularInStock) {
                filteredProducts.add(product);
            } else if(containsSpecialSize && specialInStock && regularInStock) {
                filteredProducts.add(product);
            }
        }

        Long[] productIds = filteredProducts.stream()
                .sorted(Comparator.comparingInt(ProductDTO::getSequence))
                .map(ProductDTO::getId)
                .toArray(Long[]::new);

        return productIds;
    }


    public boolean checkStock(SizeDTO size) {
        return size.getBacksoon() || (Objects.nonNull(size.getStockDTO()) && size.getStockDTO().getQuantity() > 0);
    }

}
