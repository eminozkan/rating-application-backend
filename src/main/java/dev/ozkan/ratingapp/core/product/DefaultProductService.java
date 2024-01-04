package dev.ozkan.ratingapp.core.product;

import dev.ozkan.ratingapp.business.core.result.CreationResult;
import dev.ozkan.ratingapp.business.core.result.OperationFailureReason;
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
    public CreationResult saveProduct(SaveProductServiceRequest request) {
        if (ObjectUtils.isEmpty(request.getMaker())){
            logger.debug("Product can't saved. Reason : empty maker name");
            return CreationResult.failure(OperationFailureReason.PRECONDITION_FAILED,"Empty maker name");
        }
        if (ObjectUtils.isEmpty(request.getModel())){
            logger.debug("Product can't saved. Reason : empty model name");
            return CreationResult.failure(OperationFailureReason.PRECONDITION_FAILED,"Empty model name");
        }

        if (productRepository.getProductByMakerAndModel(request.getMaker(), request.getModel()).isPresent()){
            logger.debug("Product can't saved. Reason : product already exist");
            return CreationResult.failure(OperationFailureReason.CONFLICT,"Already exist product");
        }

        var product = new Product()
                .setId(UUID.randomUUID().toString())
                .setMaker(request.getMaker())
                .setModel(request.getModel())
                .setExternalURL(request.getExternalURL())
                .setCategory(request.getCategory())
                .setBase64Image(request.getBase64Image());

        productRepository.save(product);
        return CreationResult.success("""
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
}
