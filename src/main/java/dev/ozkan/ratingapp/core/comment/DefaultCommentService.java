package dev.ozkan.ratingapp.core.comment;

import dev.ozkan.ratingapp.core.model.comment.Comment;
import dev.ozkan.ratingapp.core.model.comment.Rating;
import dev.ozkan.ratingapp.core.model.comment.UsageTime;
import dev.ozkan.ratingapp.core.product.ProductService;
import dev.ozkan.ratingapp.repository.CommentRepository;
import dev.ozkan.ratingapp.repository.ProductRepository;
import dev.ozkan.ratingapp.support.result.CreationResult;
import dev.ozkan.ratingapp.support.result.OperationFailureReason;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

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
    public CreationResult addComment(AddCommentServiceRequest request, String commentOwnerId) {
        if (Rating.getValue(request.getRating()) == null){
            logger.debug("Add Comment Failed. Reason : Wrong rating value");
            return CreationResult.failure(OperationFailureReason.PRECONDITION_FAILED,"Wrong rating value");
        }
        if (UsageTime.getUsageTimeByValue(request.getUsageTime()) == null){
            logger.debug("Add Comment Failed. Reason : Wrong usage time");
            return CreationResult.failure(OperationFailureReason.PRECONDITION_FAILED,"Wrong usage time value");
        }
        var productOptional = productRepository.getById(request.getProductId());
        if (productOptional.isEmpty()){
            logger.debug("Add Comment failed. Reason : Product {} not found.",request.getProductId());
            return CreationResult.failure(OperationFailureReason.NOT_FOUND,"Product not found.");
        }

        var commentOptional = commentRepository.getByProductIdAndUserId(request.getProductId(),commentOwnerId);
        if (commentOptional.isPresent()){
            logger.debug("Add Comment failed. Reason : User {} has already commented in this product.",commentOwnerId);
            return CreationResult.failure(OperationFailureReason.CONFLICT,"Already commented.");
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
        return CreationResult.success("Comment added successfully");
    }

    @Override
    public List<Comment> getCommentsByProductId(String productId) {
        return commentRepository.findAllByProductId(productId);
    }
}
