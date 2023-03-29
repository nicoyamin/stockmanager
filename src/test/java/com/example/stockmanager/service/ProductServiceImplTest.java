package com.example.stockmanager.service;

import com.example.stockmanager.TestUtils;
import com.example.stockmanager.model.Product;
import com.example.stockmanager.model.dto.ProductDTO;
import com.example.stockmanager.model.mapper.ProductMapper;
import com.example.stockmanager.repository.ProductRepository;
import com.example.stockmanager.utils.StockUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private StockUtils stockUtils;

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        productService = new ProductServiceImpl(productRepository, productMapper, stockUtils);
    }


    @Test
    void testGetAllProductsDTO() {
        List<Product> productList = Arrays.asList();
        List<ProductDTO> expectedProductDtoList = TestUtils.getProductDTOList();
        when(productRepository.findAll()).thenReturn(productList);
        when(productMapper.toProductDTOList(productList)).thenReturn(expectedProductDtoList);

        List<ProductDTO> actualProductDtoList = productService.getAllProducts();

        assertEquals(expectedProductDtoList, actualProductDtoList);
    }

    @Test
    void testGetProductsInStock() {
        List<ProductDTO> productList = TestUtils.getProductDTOList();
        List<Product> products = Arrays.asList();
        Long[] expectedProductIds = new Long[]{1L, 2L};

        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.toProductDTOList(products)).thenReturn(productList);
        when(stockUtils.identifyProductsInStock(productList)).thenReturn(expectedProductIds);


        Long[] actualProductIds = productService.getProductsInStock();

        assertArrayEquals(expectedProductIds, actualProductIds);
    }

}