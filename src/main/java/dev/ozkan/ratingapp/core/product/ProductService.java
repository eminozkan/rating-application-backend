package dev.ozkan.ratingapp.core.product;

import dev.ozkan.ratingapp.business.core.result.CreationResult;

public interface ProductService {
    CreationResult saveProduct(SaveProductServiceRequest request);
}
