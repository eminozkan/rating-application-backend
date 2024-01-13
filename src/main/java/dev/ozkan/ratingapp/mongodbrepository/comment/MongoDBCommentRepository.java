package dev.ozkan.ratingapp.mongodbrepository.comment;

import dev.ozkan.ratingapp.core.model.comment.Comment;
import dev.ozkan.ratingapp.core.model.product.Product;
import dev.ozkan.ratingapp.repository.CommentRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBCommentRepository extends CommentRepository, org.springframework.data.repository.Repository<Comment,String> {
}
