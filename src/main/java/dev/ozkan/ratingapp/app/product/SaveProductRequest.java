package dev.ozkan.ratingapp.app.product;

import dev.ozkan.ratingapp.config.handler.exception.WrongCategoryNameException;
import dev.ozkan.ratingapp.core.model.product.Category;
import dev.ozkan.ratingapp.core.product.SaveProductServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SaveProductRequest(
        @NotNull
        @NotBlank
        String maker,
        @NotNull
        @NotBlank
        String model,

        @NotNull
        @NotBlank
        String category,

        @NotNull
        @NotBlank
        String externalURL,

        @NotBlank
        @NotNull
        String base64Image
) {
    SaveProductServiceRequest toServiceRequest() throws WrongCategoryNameException {
        var request = new SaveProductServiceRequest()
                .setMaker(maker)
                .setModel(model)
                .setExternalURL(externalURL)
                .setBase64Image(base64Image);

        try {
            request.setCategory(Category.valueOf(category.toUpperCase()));
        } catch (Exception e) {
            throw new WrongCategoryNameException("invalid category name");
        }

        return request;
    }
}
