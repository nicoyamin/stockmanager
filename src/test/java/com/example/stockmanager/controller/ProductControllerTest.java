package com.example.stockmanager.controller;

import com.example.stockmanager.model.dto.ProductDTO;
import com.example.stockmanager.model.dto.SizeDTO;
import com.example.stockmanager.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @Mock
    private ProductServiceImpl productService;

    @InjectMocks
    private ProductController productController;

    private PodamFactory factory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new PodamFactoryImpl();
    }

    @Test
    void testGetAllProducts() {
        List<ProductDTO> productList = new ArrayList<>();
        SizeDTO sizeDTO = factory.manufacturePojo(SizeDTO.class);
        productList.add(new ProductDTO(1L, 1, Arrays.asList(sizeDTO)));
        productList.add(new ProductDTO(2L, 2, Arrays.asList(sizeDTO)));

        when(productService.getAllProductsDTO()).thenReturn(productList);

        ResponseEntity<List<ProductDTO>> response = productController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productList, response.getBody());
    }

    @Test
    void testIdentifyProductsInStock() {
        Long[] productIds = {1L, 2L, 3L};

        when(productService.getProductsInStock()).thenReturn(productIds);

        ResponseEntity<Long[]> response = productController.getProductsInStock();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productIds, response.getBody());
    }


    @Test
    void testIdentifyProductsInStock_HandleNullPointer() {
        Long[] productIds = {1L, 2L, 3L};

        when(productService.getProductsInStock()).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> {
            ResponseEntity<Long[]> response = productController.getProductsInStock();
            //System.out.println(response);
        });

    }

}