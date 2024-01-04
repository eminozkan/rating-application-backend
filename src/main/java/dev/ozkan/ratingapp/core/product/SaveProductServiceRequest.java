package dev.ozkan.ratingapp.core.product;

import dev.ozkan.ratingapp.core.model.product.Category;

public class SaveProductServiceRequest {
    private String maker;
    private String model;
    private String externalURL;
    private Category category;

    private String base64Image;

    public String getBase64Image() {
        return base64Image;
    }

    public SaveProductServiceRequest setBase64Image(String base64Image) {
        this.base64Image = base64Image;
        return this;
    }

    public String getMaker() {
        return maker;
    }

    public SaveProductServiceRequest setMaker(String maker) {
        this.maker = maker;
        return this;
    }

    public String getModel() {
        return model;
    }

    public SaveProductServiceRequest setModel(String model) {
        this.model = model;
        return this;
    }

    public String getExternalURL() {
        return externalURL;
    }

    public SaveProductServiceRequest setExternalURL(String externalURL) {
        this.externalURL = externalURL;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public SaveProductServiceRequest setCategory(Category category) {
        this.category = category;
        return this;
    }
}
