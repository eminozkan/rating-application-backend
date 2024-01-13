package dev.ozkan.ratingapp.repository;

import dev.ozkan.ratingapp.core.model.comment.Comment;

import java.util.List;

public interface CommentRepository {
    void save(Comment comment);

    List<Comment> findAllByProductId(String productId);
}
