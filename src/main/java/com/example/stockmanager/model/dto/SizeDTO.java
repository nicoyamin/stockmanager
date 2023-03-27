package com.example.stockmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SizeDTO {

    private Long id;
    private Boolean backsoon;
    private Boolean special;
    private StockDTO stockDTO;

}
