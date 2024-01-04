package dev.ozkan.ratingapp.repository;

import dev.ozkan.ratingapp.core.model.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> save(Product p);

    List<Product> findAll();

    Optional<Product> getProductByMakerAndModel(String maker,String model);

    Optional<Product> getById(String id);
}
