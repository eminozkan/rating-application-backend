package dev.ozkan.ratingapp.core.comment;


public class AddCommentServiceRequest {
    private String productId;

    private String commentText;

    private int rating;

    private String usageTime;

    public String getProductId() {
        return productId;
    }

    public AddCommentServiceRequest setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public String getCommentText() {
        return commentText;
    }

    public AddCommentServiceRequest setCommentText(String commentText) {
        this.commentText = commentText;
        return this;
    }

    public int getRating() {
        return rating;
    }

    public AddCommentServiceRequest setRating(int rating) {
        this.rating = rating;
        return this;
    }

    public String getUsageTime() {
        return usageTime;
    }

    public AddCommentServiceRequest setUsageTime(String usageTime) {
        this.usageTime = usageTime;
        return this;
    }
}
