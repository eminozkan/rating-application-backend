package dev.ozkan.ratingapp.app.product;

import dev.ozkan.ratingapp.config.handler.exception.WrongCategoryNameException;
import dev.ozkan.ratingapp.core.model.product.Category;
import dev.ozkan.ratingapp.core.product.SaveProductServiceRequest;

public record SaveProductRequest(
        String maker,
        String model,
        String category,
        String externalURL
) {
    SaveProductServiceRequest toServiceRequest() throws WrongCategoryNameException {
        var request = new SaveProductServiceRequest()
                .setMaker(maker)
                .setModel(model)
                .setExternalURL(externalURL);

        try{
            request.setCategory(Category.valueOf(category));
        }catch (Exception e){
            throw new WrongCategoryNameException("invalid category name");
        }

        return request;
    }
}
