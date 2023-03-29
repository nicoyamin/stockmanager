package com.example.stockmanager.utils;

import com.example.stockmanager.model.dto.ProductDTO;
import com.example.stockmanager.model.dto.SizeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StockUtilsTest {

    private StockUtils stockUtils;

    private PodamFactory factory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        stockUtils = new StockUtils();
        factory = new PodamFactoryImpl();
    }




    //Test case 1 - no special size, regular in stock -> show
    @Test
    public void shouldTestIdentifyProducts_NoSpecialSize_RegularInStock_ThenShow() {
        List<ProductDTO> productList = new ArrayList<>();
        SizeDTO sizeDTO = factory.manufacturePojo(SizeDTO.class);
        productList.add(new ProductDTO(1L, 1, Arrays.asList(sizeDTO)));
        productList.add(new ProductDTO(2L, 2, Arrays.asList(sizeDTO)));

        List<ProductDTO> modifiedProducts = productList.stream()
                .map(product -> {
                    List<SizeDTO> modifiedSizes = product.getSizesDTO().stream()
                            .map(size -> {
                                size.setSpecial(false);
                                size.getStockDTO().setQuantity(1);
                                return size;
                            })
                            .collect(Collectors.toList());
                    product.setSizesDTO(modifiedSizes);
                    return product;
                })
                .collect(Collectors.toList());

        Long[] expectedResult = new Long[]{1L, 2L};
        Long[] actualResult = stockUtils.identifyProductsInStock(modifiedProducts);


        assertEquals(expectedResult[0], actualResult[0]);
        assertEquals(expectedResult[1], actualResult[1]);

    }

    //Test case 2 - no special size, regular not in stock -> do not show
    @Test
    public void shouldTestIdentifyProducts_NoSpecialSize_RegularNotInStock_ThenDoNotShow() {
        List<ProductDTO> productList = new ArrayList<>();
        SizeDTO sizeDTO = factory.manufacturePojo(SizeDTO.class);
        productList.add(new ProductDTO(1L, 1, Arrays.asList(sizeDTO)));
        productList.add(new ProductDTO(2L, 2, Arrays.asList(sizeDTO)));

        List<ProductDTO> modifiedProducts = productList.stream()
                .map(product -> {
                    List<SizeDTO> modifiedSizes = product.getSizesDTO().stream()
                            .map(size -> {
                                size.setSpecial(false);
                                size.setBacksoon(false);
                                size.getStockDTO().setQuantity(0);
                                return size;
                            })
                            .collect(Collectors.toList());
                    product.setSizesDTO(modifiedSizes);
                    return product;
                })
                .collect(Collectors.toList());

        Long[] actualResult = stockUtils.identifyProductsInStock(modifiedProducts);

        assertTrue(actualResult.length == 0);

    }

    //Test case 3 - has special size, special not in stock, regular not in stock -> do not show
    @Test
    public void shouldTestIdentifyProducts_HasSpecialSize_SpecialNotInStock_RegularNotInStock_ThenDoNotShow() {
        List<ProductDTO> productList = new ArrayList<>();
        //special
        SizeDTO sizeDTO1 = factory.manufacturePojo(SizeDTO.class);
        sizeDTO1.setSpecial(true);
        sizeDTO1.setBacksoon(false);
        sizeDTO1.getStockDTO().setQuantity(0);
        //regular
        SizeDTO sizeDTO2 = factory.manufacturePojo(SizeDTO.class);
        sizeDTO2.setSpecial(false);
        sizeDTO2.setBacksoon(false);
        sizeDTO2.getStockDTO().setQuantity(0);

        productList.add(new ProductDTO(1L, 1, Arrays.asList(sizeDTO1, sizeDTO2)));


        Long[] actualResult = stockUtils.identifyProductsInStock(productList);

        assertTrue(actualResult.length == 0);

    }

    //Test case 4 - has special size, special not in stock, regular in stock -> do not show
    @Test
    public void shouldTestIdentifyProducts_HasSpecialSize_SpecialNotInStock_RegularInStock_ThenDoNotShow() {
        List<ProductDTO> productList = new ArrayList<>();
        //special
        SizeDTO sizeDTO1 = factory.manufacturePojo(SizeDTO.class);
        sizeDTO1.setSpecial(true);
        sizeDTO1.setBacksoon(false);
        sizeDTO1.getStockDTO().setQuantity(0);
        //regular
        SizeDTO sizeDTO2 = factory.manufacturePojo(SizeDTO.class);
        sizeDTO2.setSpecial(false);
        sizeDTO2.setBacksoon(false);
        sizeDTO2.getStockDTO().setQuantity(1);

        productList.add(new ProductDTO(1L, 1, Arrays.asList(sizeDTO1, sizeDTO2)));

        Long[] actualResult = stockUtils.identifyProductsInStock(productList);

        assertTrue(actualResult.length == 0);

    }

    //Test case 5 - has special size, special in stock, regular not in stock -> do not show
    @Test
    public void shouldTestIdentifyProducts_HasSpecialSize_SpecialInStock_RegularNotInStock_ThenDoNotShow() {
        List<ProductDTO> productList = new ArrayList<>();
        //special
        SizeDTO sizeDTO1 = factory.manufacturePojo(SizeDTO.class);
        sizeDTO1.setSpecial(true);
        sizeDTO1.setBacksoon(true);
        sizeDTO1.getStockDTO().setQuantity(0);
        //regular
        SizeDTO sizeDTO2 = factory.manufacturePojo(SizeDTO.class);
        sizeDTO2.setSpecial(false);
        sizeDTO2.setBacksoon(false);
        sizeDTO2.getStockDTO().setQuantity(0);

        productList.add(new ProductDTO(1L, 1, Arrays.asList(sizeDTO1, sizeDTO2)));

        Long[] actualResult = stockUtils.identifyProductsInStock(productList);

        assertTrue(actualResult.length == 0);

    }

    //Test case 6 - has special size, special in stock, regular in stock -> show
    @Test
    public void shouldTestIdentifyProducts_HasSpecialSize_SpecialInStock_RegularInStock_ThenShow() {
        List<ProductDTO> productList = new ArrayList<>();
        //special
        SizeDTO sizeDTO1 = factory.manufacturePojo(SizeDTO.class);
        sizeDTO1.setSpecial(true);
        sizeDTO1.setBacksoon(true);
        sizeDTO1.getStockDTO().setQuantity(0);
        //regular
        SizeDTO sizeDTO2 = factory.manufacturePojo(SizeDTO.class);
        sizeDTO2.setSpecial(false);
        sizeDTO2.setBacksoon(false);
        sizeDTO2.getStockDTO().setQuantity(1);

        productList.add(new ProductDTO(1L, 1, Arrays.asList(sizeDTO1, sizeDTO2)));

        Long[] actualResult = stockUtils.identifyProductsInStock(productList);

        assertEquals(actualResult[0], 1L);

    }

    @Test
    public void shouldTestIdentifyProducts_InvalidData_continueToNextElement_ThenShow() {
        List<ProductDTO> productList = new ArrayList<>();
        //special - special is null, so this size will be ignored
        SizeDTO sizeDTO1 = factory.manufacturePojo(SizeDTO.class);
        sizeDTO1.setSpecial(null);
        sizeDTO1.setBacksoon(false);
        sizeDTO1.getStockDTO().setQuantity(1);
        //regular
        SizeDTO sizeDTO2 = factory.manufacturePojo(SizeDTO.class);
        sizeDTO2.setSpecial(false);
        sizeDTO2.setBacksoon(false);
        sizeDTO2.getStockDTO().setQuantity(1);

        productList.add(new ProductDTO(1L, 1, Arrays.asList(sizeDTO1, sizeDTO2)));

        Long[] actualResult = stockUtils.identifyProductsInStock(productList);

        assertEquals(actualResult[0], 1L);

    }

    @Test
    public void shouldTestIdentifyProducts_BackSoonNull_continueToNextElement_ThenShow() {
        List<ProductDTO> productList = new ArrayList<>();
        //special - back soon is null, but stock is 0, so product won't show
        SizeDTO sizeDTO1 = factory.manufacturePojo(SizeDTO.class);
        sizeDTO1.setSpecial(true);
        sizeDTO1.setBacksoon(null);
        sizeDTO1.getStockDTO().setQuantity(0);
        //regular
        SizeDTO sizeDTO2 = factory.manufacturePojo(SizeDTO.class);
        sizeDTO2.setSpecial(false);
        sizeDTO2.setBacksoon(false);
        sizeDTO2.getStockDTO().setQuantity(1);

        productList.add(new ProductDTO(1L, 1, Arrays.asList(sizeDTO1, sizeDTO2)));

        Long[] actualResult = stockUtils.identifyProductsInStock(productList);

        assertTrue(actualResult.length == 0);

    }

}