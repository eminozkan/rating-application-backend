package dev.ozkan.ratingapp.mongodbrepository.product;

import dev.ozkan.ratingapp.core.model.product.Product;
import dev.ozkan.ratingapp.repository.ProductRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBProductRepository extends ProductRepository,org.springframework.data.repository.Repository<Product,String> {
}
