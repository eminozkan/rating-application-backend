package dev.ozkan.ratingapp.core.product;

import dev.ozkan.ratingapp.business.core.result.OperationFailureReason;
import dev.ozkan.ratingapp.config.handler.exception.WrongCategoryNameException;
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

import java.util.List;
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
            assertEquals(request.getMaker(), savedProduct.getMaker());
            assertEquals(request.getModel(), savedProduct.getModel());
            assertEquals(request.getExternalURL(), savedProduct.getExternalURL());
            assertEquals(request.getCategory(), savedProduct.getCategory());

        }


    }


    @Nested
    class GetProducts {

        @DisplayName("Empty filter text")
        @Test
        void emptyFilterText() throws WrongCategoryNameException {
            Product p1 = new Product()
                    .setMaker("Xiaomi");
            Product p2 = new Product()
                    .setMaker("Iphone");
            Mockito.doReturn(List.of(p1, p2))
                    .when(repository)
                    .findAll();

            var products = service.getProducts("");
            assertThat(products).isNotEmpty();
            assertThat(products).contains(p1);
            assertThat(products).contains(p2);
        }

        @DisplayName("Wrong category text")
        @Test
        void wrongCategoryName() {
            try {
                var products = service.getProducts("WrongCategory");
            } catch (WrongCategoryNameException ex) {
                assertThat(ex.getMessage()).isNotNull().isNotEmpty();
            } catch (Exception ex) {
                fail("Unknown exception throwed");
            }


        }

        @DisplayName("With filter text")
        @Test
        void withFilterText() throws WrongCategoryNameException {
            Product p1 = new Product()
                    .setMaker("Xiaomi")
                    .setModel("Redmi Note 9 Pro")
                    .setCategory(Category.PHONE);
            Product p2 = new Product()
                    .setMaker("Xiaomi")
                    .setModel("Mi band 4")
                    .setCategory(Category.SMART_WATCH);
            Mockito.doReturn(List.of(p1, p2))
                    .when(repository)
                    .findAll();

            var products = service.getProducts("PHONE");
            assertThat(products).isNotEmpty();
            assertThat(products).contains(p1);
            assertFalse(products.contains(p2));
        }

    }


    @Nested
    class GetProduct {

        @DisplayName("Get product with Wrong ID")
        @Test
        void wrongId() {
            String id = "wrong-product-id";
            Mockito.doReturn(Optional.empty())
                    .when(repository)
                    .getById(id);

            var result = service.getProduct(id);
            assertThat(result).isEmpty();
        }

        @DisplayName("Get product")
        @Test
        void getProduct() {
            String id = "product-id";
            Product p = new Product()
                    .setId("id")
                    .setMaker("maker");
            Mockito.doReturn(Optional.of(p))
                    .when(repository)
                    .getById(id);

            var result = service.getProduct(id);
            assertThat(result).isNotEmpty();
            assertThat(result.get()).isEqualTo(p);
        }
    }
}