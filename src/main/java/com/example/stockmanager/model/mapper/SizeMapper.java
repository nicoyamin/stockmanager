package com.example.stockmanager.model.mapper;

import com.example.stockmanager.model.Size;
import com.example.stockmanager.model.dto.SizeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SizeMapper {
    SizeMapper INSTANCE = Mappers.getMapper(SizeMapper.class);

    @Mapping(source = "stock", target = "stockDTO")
    SizeDTO toSizeDTO(Size size);
}
