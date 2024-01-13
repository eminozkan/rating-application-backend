package dev.ozkan.ratingapp.support.response;

import dev.ozkan.ratingapp.core.model.comment.Rating;
import dev.ozkan.ratingapp.core.model.comment.UsageTime;

import java.time.Instant;

public class CommentResponse {
    private String commentId;

    private String commentText;

    private Rating rating;

    private UsageTime usageTime;

    private Instant createdAt;


    public String getCommentId() {
        return commentId;
    }

    public CommentResponse setCommentId(String commentId) {
        this.commentId = commentId;
        return this;
    }

    public String getCommentText() {
        return commentText;
    }

    public CommentResponse setCommentText(String commentText) {
        this.commentText = commentText;
        return this;
    }

    public Rating getRating() {
        return rating;
    }

    public CommentResponse setRating(Rating rating) {
        this.rating = rating;
        return this;
    }

    public UsageTime getUsageTime() {
        return usageTime;
    }

    public CommentResponse setUsageTime(UsageTime usageTime) {
        this.usageTime = usageTime;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public CommentResponse setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
