package com.example.stockmanager.utils;

import com.example.stockmanager.model.dto.ProductDTO;
import com.example.stockmanager.model.dto.SizeDTO;
import com.example.stockmanager.model.dto.StockDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StockUtilsTest {
    @Mock
    private ProductDTO productDTO1;
    @Mock
    private ProductDTO productDTO2;
    @Mock
    private SizeDTO sizeDTO1;
    @Mock
    private SizeDTO sizeDTO2;
    @Mock
    private StockDTO stockDTO;

    private StockUtils stockUtils;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        stockUtils = new StockUtils();
    }

}