package dev.ozkan.ratingapp.core.comment;

import dev.ozkan.ratingapp.core.model.comment.Comment;
import dev.ozkan.ratingapp.support.result.CrudResult;

import java.util.List;

public interface CommentService {
    CrudResult addComment(AddCommentServiceRequest request, String commentOwnerId);

    List<Comment> getCommentsByProductId(String productId);
}
