package dev.ozkan.ratingapp.core.comment;

public class DeleteCommentServiceRequest {
    private String commentId;

    public String getCommentId() {
        return commentId;
    }

    public DeleteCommentServiceRequest setCommentId(String commentId) {
        this.commentId = commentId;
        return this;
    }
}
