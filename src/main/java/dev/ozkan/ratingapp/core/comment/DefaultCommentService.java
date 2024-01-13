package dev.ozkan.ratingapp.core.comment;

import dev.ozkan.ratingapp.core.model.comment.Comment;
import dev.ozkan.ratingapp.core.model.comment.Rating;
import dev.ozkan.ratingapp.core.model.comment.UsageTime;
import dev.ozkan.ratingapp.core.model.user.UserRole;
import dev.ozkan.ratingapp.core.product.ProductService;
import dev.ozkan.ratingapp.repository.CommentRepository;
import dev.ozkan.ratingapp.repository.ProductRepository;
import dev.ozkan.ratingapp.support.result.CrudResult;
import dev.ozkan.ratingapp.support.result.OperationFailureReason;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultCommentService implements CommentService{
    private final ProductRepository productRepository;

    private final CommentRepository commentRepository;

    private final ProductService productService;

    private final Logger logger = LogManager.getLogger();

    public DefaultCommentService(ProductRepository productRepository, CommentRepository commentRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.commentRepository = commentRepository;
        this.productService = productService;
    }


    @Override
    public CrudResult addComment(AddCommentServiceRequest request, String commentOwnerId) {
        if (Rating.getValue(request.getRating()) == null){
            logger.debug("Add Comment Failed. Reason : Wrong rating value");
            return CrudResult.failure(OperationFailureReason.PRECONDITION_FAILED,"Wrong rating value");
        }
        if (UsageTime.getUsageTimeByValue(request.getUsageTime()) == null){
            logger.debug("Add Comment Failed. Reason : Wrong usage time");
            return CrudResult.failure(OperationFailureReason.PRECONDITION_FAILED,"Wrong usage time value");
        }
        var productOptional = productRepository.getById(request.getProductId());
        if (productOptional.isEmpty()){
            logger.debug("Add Comment failed. Reason : Product {} not found.",request.getProductId());
            return CrudResult.failure(OperationFailureReason.NOT_FOUND,"Product not found.");
        }

        var commentOptional = commentRepository.getByProductIdAndUserId(request.getProductId(),commentOwnerId);
        if (commentOptional.isPresent()){
            logger.debug("Add Comment failed. Reason : User {} has already commented in this product.",commentOwnerId);
            return CrudResult.failure(OperationFailureReason.CONFLICT,"Already commented.");
        }

        productService.updateProductRating(request.getProductId(), request.getRating());
        var createdComment = new Comment()
                .setCommentId(UUID.randomUUID().toString())
                .setCommentText(request.getCommentText())
                .setRating(Rating.getValue(request.getRating()))
                .setProductId(request.getProductId())
                .setUsageTime(UsageTime.getUsageTimeByValue(request.getUsageTime()))
                .setCreatedAt(Instant.now())
                .setUserId(commentOwnerId);

        commentRepository.save(createdComment);
        return CrudResult.success("Comment added successfully");
    }

    @Override
    public List<Comment> getCommentsByProductId(String productId) {
        return commentRepository.findAllByProductId(productId);
    }

    @Override
    public CrudResult deleteComment(DeleteCommentServiceRequest request, String userId,UserRole role) {
        var commentOptional = commentRepository.getByCommentId(request.getCommentId());
        if (commentOptional.isEmpty()){
            logger.debug("Delete comment failed. Reason : Comment {} not found.",request.getCommentId());
            return CrudResult.failure(OperationFailureReason.NOT_FOUND,"Comment not found");
        }
        var comment = commentOptional.get();
        if (!comment.getUserId().equals(userId) && role != UserRole.ROLE_ADMIN){
            logger.debug("Delete comment failed. Reason : User {} is not comment owner.",request.getCommentId());
            return CrudResult.failure(OperationFailureReason.UNAUTHORIZED,"Not authorized user.");
        }

        productService.reduceProductRating(comment);
        commentRepository.deleteById(request.getCommentId());
        return CrudResult.success();
    }
}
