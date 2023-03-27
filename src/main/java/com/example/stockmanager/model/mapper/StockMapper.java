package com.example.stockmanager.model.mapper;

import com.example.stockmanager.model.Stock;
import com.example.stockmanager.model.dto.StockDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StockMapper {

    StockMapper INSTANCE = Mappers.getMapper( StockMapper.class );

    @Mapping(source = "size_id", target = "sizeId")
    StockDTO toStockDTO(Stock stock);

}
