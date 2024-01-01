package dev.ozkan.ratingapp.core.product;

import dev.ozkan.ratingapp.business.core.result.OperationFailureReason;
import dev.ozkan.ratingapp.core.model.product.Category;
import dev.ozkan.ratingapp.core.model.product.Product;
import dev.ozkan.ratingapp.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DefaultProductServiceTest {

    @Mock
    ProductRepository repository;

    DefaultProductService service;

    @BeforeEach
    void setUp() {
        service = new DefaultProductService(repository);
    }

    @Nested
    class Save {

        @Captor
        ArgumentCaptor<Product> productArgumentCaptor;

        @DisplayName("Try to Save with Empty Maker Name")
        @Test
        void emptyMakerName() {
            var request = new SaveProductServiceRequest()
                    .setMaker("")
                    .setModel("model")
                    .setExternalURL("url")
                    .setCategory(Category.PHONE);

            var result = service.saveProduct(request);

            assertFalse(result.isSuccess());
            assertEquals(OperationFailureReason.PRECONDITION_FAILED, result.getReason());
        }


        @DisplayName("Try to save with empty model name")
        @Test
        void emptyModelName() {
            var request = new SaveProductServiceRequest()
                    .setMaker("maker")
                    .setModel("")
                    .setExternalURL("url")
                    .setCategory(Category.PHONE);

            var result = service.saveProduct(request);

            assertFalse(result.isSuccess());
            assertEquals(OperationFailureReason.PRECONDITION_FAILED, result.getReason());
        }

        @DisplayName("Try to save with same maker, same model")
        @Test
        void sameObject() {
            var request = new SaveProductServiceRequest()
                    .setMaker("maker")
                    .setModel("model")
                    .setExternalURL("url")
                    .setCategory(Category.PHONE);

            Mockito.doReturn(Optional.of(new Product()))
                    .when(repository)
                    .getProductByMakerAndModel(request.getMaker(), request.getModel());


            var result = service.saveProduct(request);

            assertFalse(result.isSuccess());
            assertEquals(OperationFailureReason.CONFLICT, result.getReason());
        }

        @DisplayName("Success")
        @Test
        void success() {
            var request = new SaveProductServiceRequest()
                    .setMaker("maker")
                    .setModel("model")
                    .setExternalURL("url")
                    .setCategory(Category.PHONE);

            Mockito.doReturn(Optional.empty())
                    .when(repository)
                    .getProductByMakerAndModel(request.getMaker(), request.getModel());


            var result = service.saveProduct(request);

            assertTrue(result.isSuccess());
            Mockito.verify(repository)
                    .save(productArgumentCaptor.capture());

            var savedProduct = productArgumentCaptor.getValue();

            assertThat(savedProduct.getId()).isNotEmpty().isNotNull();
            assertEquals(request.getMaker(),savedProduct.getMaker());
            assertEquals(request.getModel(),savedProduct.getModel());
            assertEquals(request.getExternalURL(),savedProduct.getExternalURL());
            assertEquals(request.getCategory(),savedProduct.getCategory());

        }


    }


}