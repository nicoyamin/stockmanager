package com.example.stockmanager;

import com.example.stockmanager.model.Product;
import com.example.stockmanager.model.dto.ProductDTO;
import com.example.stockmanager.model.dto.SizeDTO;
import com.example.stockmanager.model.dto.StockDTO;
import lombok.Builder;

import java.util.Arrays;
import java.util.List;

public class TestUtils {

    public static List<ProductDTO> getProductDTOList() {
        ProductDTO productDTO1 = ProductDTO.builder()
                .id(1L)
                .sequence(1)
                .sizesDTO(getSizeDTOList())
                .build();

        ProductDTO productDTO2 = ProductDTO.builder()
                .id(2L)
                .sequence(2)
                .sizesDTO(getSizeDTOList())
                .build();

        return Arrays.asList(productDTO1, productDTO2);
    }



    public static List<SizeDTO> getSizeDTOList() {

        SizeDTO sizeDTO1 = SizeDTO.builder()
                .id(1L)
                .backsoon(true)
                .special(true)
                .stockDTO(getStockDTO())
                .build();

        SizeDTO sizeDTO2 = SizeDTO.builder()
                .id(2L)
                .backsoon(false)
                .special(false)
                .stockDTO(getStockDTO())
                .build();

        return Arrays.asList(sizeDTO1, sizeDTO2);
    }

    public static StockDTO getStockDTO() {
        return StockDTO.builder().size_id(1L).quantity(3).build();
    }
}
