package dev.ozkan.ratingapp.repository;

import dev.ozkan.ratingapp.core.model.comment.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    void save(Comment comment);

    List<Comment> findAllByProductId(String productId);

    Optional<Comment> getByProductIdAndUserId(String productId, String commentOwnerId);

    Optional<Comment> getByCommentId(String commentId);

    void deleteById(String commentId);
}
