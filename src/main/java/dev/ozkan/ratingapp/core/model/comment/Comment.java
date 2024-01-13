package dev.ozkan.ratingapp.core.model.comment;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.Instant;

public class Comment {
    @Id
    private String commentId;

    @Indexed
    private String productId;

    private String userId;

    private String commentText;

    private Rating rating;

    private UsageTime usageTime;

    private Instant createdAt;

    public String getCommentId() {
        return commentId;
    }

    public Comment setCommentId(String commentId) {
        this.commentId = commentId;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public Comment setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Comment setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getCommentText() {
        return commentText;
    }

    public Comment setCommentText(String commentText) {
        this.commentText = commentText;
        return this;
    }

    public Rating getRating() {
        return rating;
    }

    public Comment setRating(Rating rating) {
        this.rating = rating;
        return this;
    }

    public UsageTime getUsageTime() {
        return usageTime;
    }

    public Comment setUsageTime(UsageTime usageTime) {
        this.usageTime = usageTime;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Comment setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
