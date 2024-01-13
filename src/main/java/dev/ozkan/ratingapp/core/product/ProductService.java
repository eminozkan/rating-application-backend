package dev.ozkan.ratingapp.core.product;

import dev.ozkan.ratingapp.support.result.CreationResult;
import dev.ozkan.ratingapp.config.handler.exception.WrongCategoryNameException;
import dev.ozkan.ratingapp.core.model.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    CreationResult saveProduct(SaveProductServiceRequest request);

    List<Product> getProducts(String filterText) throws WrongCategoryNameException;

    Optional<Product> getProduct(String productId);
}
