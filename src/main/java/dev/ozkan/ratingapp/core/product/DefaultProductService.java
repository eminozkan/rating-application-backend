package dev.ozkan.ratingapp.core.product;

import dev.ozkan.ratingapp.business.core.result.CreationResult;
import dev.ozkan.ratingapp.business.core.result.OperationFailureReason;
import dev.ozkan.ratingapp.core.model.product.Product;
import dev.ozkan.ratingapp.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
                .setCategory(request.getCategory());

        productRepository.save(product);
        return CreationResult.success("""
                    { "message" : "Product has been saved successfully"}
                """);
    }
}
