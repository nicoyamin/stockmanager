package com.example.stockmanager.model.mapper;

import com.example.stockmanager.model.Product;
import com.example.stockmanager.model.Size;
import com.example.stockmanager.model.dto.ProductDTO;
import com.example.stockmanager.model.dto.SizeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",  uses = {SizeMapper.class, StockMapper.class})
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "sizes", target = "sizesDTO")
    ProductDTO toProductDTO(Product product);

    List<ProductDTO> toProductDTOList(List<Product> products);

    default List<SizeDTO> toSizeDTOList(List<Size> sizes) {
        return sizes.stream()
                .map(SizeMapper.INSTANCE::toSizeDTO)
                .collect(Collectors.toList());
    }
}
