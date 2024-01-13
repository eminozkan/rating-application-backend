package dev.ozkan.ratingapp.core.model.product;

import dev.ozkan.ratingapp.core.model.comment.Rating;
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

    private double ratingOutOfFive;

    private long commentCount;

    private String base64Image;

    public String getBase64Image() {
        return base64Image;
    }

    public Product setBase64Image(String base64Image) {
        this.base64Image = base64Image;
        return this;
    }

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

    public double getRatingOutOfFive() {
        return ratingOutOfFive;
    }

    public Product setRatingOutOfFive(double ratingOutOfFive) {
        this.ratingOutOfFive = ratingOutOfFive;
        return this;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public Product setCommentCount(long commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public void updateRating(int ratingOutOfFive) {
        double totalRating = this.ratingOutOfFive * commentCount;
        commentCount++;
        totalRating+= ratingOutOfFive;
        this.ratingOutOfFive = totalRating / commentCount;
    }

    public void reduceProductRating(Rating rating) {
        double totalRating = this.ratingOutOfFive * commentCount;
        commentCount--;
        totalRating -= Rating.value(rating);
        this.ratingOutOfFive = totalRating / commentCount;
    }
}
