package dev.ozkan.ratingapp.core.product;

import dev.ozkan.ratingapp.core.model.comment.Comment;
import dev.ozkan.ratingapp.support.result.CrudResult;
import dev.ozkan.ratingapp.support.result.OperationFailureReason;
import dev.ozkan.ratingapp.config.handler.exception.WrongCategoryNameException;
import dev.ozkan.ratingapp.core.model.product.Category;
import dev.ozkan.ratingapp.core.model.product.Product;
import dev.ozkan.ratingapp.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultProductService implements ProductService{

    private final ProductRepository productRepository;

    private final Logger logger = LogManager.getLogger();

    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public CrudResult saveProduct(SaveProductServiceRequest request) {
        if (ObjectUtils.isEmpty(request.getMaker())){
            logger.debug("Product can't saved. Reason : empty maker name");
            return CrudResult.failure(OperationFailureReason.PRECONDITION_FAILED,"Empty maker name");
        }
        if (ObjectUtils.isEmpty(request.getModel())){
            logger.debug("Product can't saved. Reason : empty model name");
            return CrudResult.failure(OperationFailureReason.PRECONDITION_FAILED,"Empty model name");
        }

        if (productRepository.getProductByMakerAndModel(request.getMaker(), request.getModel()).isPresent()){
            logger.debug("Product can't saved. Reason : product already exist");
            return CrudResult.failure(OperationFailureReason.CONFLICT,"Already exist product");
        }

        var product = new Product()
                .setId(UUID.randomUUID().toString())
                .setMaker(request.getMaker())
                .setModel(request.getModel())
                .setExternalURL(request.getExternalURL())
                .setCategory(request.getCategory())
                .setBase64Image(request.getBase64Image())
                .setCommentCount(0L)
                .setRatingOutOfFive(0.0);

        productRepository.save(product);
        return CrudResult.success("""
                    { "message" : "Product has been saved successfully"}
                """);
    }

    @Override
    public List<Product> getProducts(String filterText) throws WrongCategoryNameException {
        if (ObjectUtils.isEmpty(filterText)){
            return productRepository.findAll();
        }

        Category category = getCategory(filterText);
        var products = productRepository.findAll();
        var result = products.stream()
                .filter(
                        (product -> product.getCategory().equals(category))
                );

        return result.toList();
    }

    private Category getCategory(String filterText) throws WrongCategoryNameException {
        Category category;
        try{
            category = Category.valueOf(filterText.toUpperCase());
        }catch (Exception ex){
            throw new WrongCategoryNameException("invalid category name");
        }
        return category;
    }

    @Override
    public Optional<Product> getProduct(String productId) {
        return productRepository.getById(productId);
    }

    @Override
    public CrudResult updateProductRating(String productId, int ratingOutOfFive) {
        var productOptional = productRepository.getById(productId);
        if (productOptional.isEmpty()){
            return CrudResult.failure(OperationFailureReason.NOT_FOUND,"product not found by id");
        }
        var productFromDb = productOptional.get();
        productFromDb.updateRating(ratingOutOfFive);

        roundRatingPrecision(productFromDb);

        productRepository.save(productFromDb);
        return CrudResult.success();
    }

    private static void roundRatingPrecision(Product productFromDb) {
        double rating = productFromDb.getRatingOutOfFive();
        rating = Math.round(rating * Math.pow(10, 2)) / Math.pow(10, 2);
        productFromDb.setRatingOutOfFive(rating);
    }

    @Override
    public CrudResult reduceProductRating(Comment comment) {
        var productOptional = productRepository.getById(comment.getProductId());
        if (productOptional.isEmpty()){
            return CrudResult.failure(OperationFailureReason.NOT_FOUND,"product not found by id");
        }
        var productFromDb = productOptional.get();
        productFromDb.reduceProductRating(comment.getRating());

        roundRatingPrecision(productFromDb);

        productRepository.save(productFromDb);
        return CrudResult.success();
    }

    @Override
    public List<Category> getCategories() {
        return List.of(Category.values());
    }

    @Override
    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }
}
