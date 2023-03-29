package com.example.stockmanager.utils;

import com.example.stockmanager.model.dto.ProductDTO;
import com.example.stockmanager.model.dto.SizeDTO;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Component
public class StockUtils {
    public Long[] identifyProductsInStock(List<ProductDTO> products) {
        return products.stream()
                .filter(product -> {
                    boolean regularInStock = false;
                    boolean containsSpecialSize = false;
                    boolean specialInStock = false;
                    for(SizeDTO size : product.getSizesDTO()) {
                        try{
                            if(size.getSpecial()) {
                                containsSpecialSize = true;
                                specialInStock = checkStock(size) || specialInStock;
                            } else {
                                regularInStock = checkStock(size) || regularInStock;
                            }
                        } catch (Exception e) {
                            continue;
                        }

                    }
                    return (!containsSpecialSize && regularInStock)
                            || (containsSpecialSize && specialInStock && regularInStock);
                })
                .sorted(Comparator.comparingInt(ProductDTO::getSequence))
                .map(ProductDTO::getId)
                .toArray(Long[]::new);
    }

    private boolean checkStock(SizeDTO size) {
        return (Objects.nonNull(size.getBacksoon()) && size.getBacksoon()) || (Objects.nonNull(size.getStockDTO()) && size.getStockDTO().getQuantity() > 0);
    }

}
