package dev.ozkan.ratingapp.core.model.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class Product {
    @Id
    private String id;

    @Indexed
    private String maker;

    @Indexed
    private String model;

    private String externalURL;

    private Category category;

    public String getId() {
        return id;
    }

    public Product setId(String id) {
        this.id = id;
        return this;
    }

    public String getMaker() {
        return maker;
    }

    public Product setMaker(String maker) {
        this.maker = maker;
        return this;
    }

    public String getModel() {
        return model;
    }

    public Product setModel(String model) {
        this.model = model;
        return this;
    }

    public String getExternalURL() {
        return externalURL;
    }

    public Product setExternalURL(String externalURL) {
        this.externalURL = externalURL;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Product setCategory(Category category) {
        this.category = category;
        return this;
    }
}
