package dev.ozkan.ratingapp.core.product;

import dev.ozkan.ratingapp.config.handler.exception.WrongCategoryNameException;
import dev.ozkan.ratingapp.core.model.comment.Comment;
import dev.ozkan.ratingapp.core.model.product.Category;
import dev.ozkan.ratingapp.core.model.product.Product;
import dev.ozkan.ratingapp.support.result.CrudResult;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    CrudResult saveProduct(SaveProductServiceRequest request);

    List<Product> getProducts(String filterText) throws WrongCategoryNameException;

    Optional<Product> getProduct(String productId);

    CrudResult updateProductRating(String productId, int ratingOutOfFive);

    CrudResult reduceProductRating(Comment comment);

    List<Category> getCategories();
}
