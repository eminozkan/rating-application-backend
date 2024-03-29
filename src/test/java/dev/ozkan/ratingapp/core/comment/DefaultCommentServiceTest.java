package dev.ozkan.ratingapp.core.comment;

import dev.ozkan.ratingapp.core.model.comment.Comment;
import dev.ozkan.ratingapp.core.model.comment.Rating;
import dev.ozkan.ratingapp.core.model.comment.UsageTime;
import dev.ozkan.ratingapp.core.model.product.Product;
import dev.ozkan.ratingapp.core.model.user.UserRole;
import dev.ozkan.ratingapp.core.product.ProductService;
import dev.ozkan.ratingapp.repository.CommentRepository;
import dev.ozkan.ratingapp.repository.ProductRepository;
import dev.ozkan.ratingapp.support.result.OperationFailureReason;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DefaultCommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductService productService;

    private DefaultCommentService service;


    @BeforeEach
    void setUp() {
        service = new DefaultCommentService(productRepository, commentRepository, productService);
    }


    @Nested
    class AddComment {

        @Captor
        ArgumentCaptor<Comment> commentArgumentCaptor;

        @DisplayName("Wrong Rating Value")
        @Test
        void wrongRating() {
            AddCommentServiceRequest request = new AddCommentServiceRequest()
                    .setRating(-1);

            var result = service.addComment(request, "user-id");
            assertFalse(result.isSuccess());
            assertEquals(OperationFailureReason.PRECONDITION_FAILED, result.getReason());
        }

        @DisplayName("Wrong Usage Time")
        @Test
        void wrongUsageTime() {
            AddCommentServiceRequest request = new AddCommentServiceRequest()
                    .setRating(4)
                    .setUsageTime("0-12");

            var result = service.addComment(request, "user-id");
            assertFalse(result.isSuccess());
            assertEquals(OperationFailureReason.PRECONDITION_FAILED, result.getReason());
        }

        @DisplayName("Wrong Product Id")
        @Test
        void wrongProductId() {
            AddCommentServiceRequest request = new AddCommentServiceRequest()
                    .setRating(4)
                    .setUsageTime("3-6")
                    .setProductId("wrong-product-id");

            Mockito.doReturn(Optional.empty())
                    .when(productRepository)
                    .getById(request.getProductId());

            var result = service.addComment(request, "user-id");
            assertFalse(result.isSuccess());
            assertEquals(OperationFailureReason.NOT_FOUND, result.getReason());
        }

        @DisplayName("Already commented")
        @Test
        void alreadyCommented() {
            AddCommentServiceRequest request = new AddCommentServiceRequest()
                    .setRating(4)
                    .setUsageTime("3-6")
                    .setProductId("product-id");

            Mockito.doReturn(Optional.of(new Product()))
                    .when(productRepository)
                    .getById(request.getProductId());

            Mockito.doReturn(Optional.of(new Comment()))
                    .when(commentRepository)
                    .getByProductIdAndUserId(request.getProductId(),"user-id");

            var result = service.addComment(request, "user-id");
            assertFalse(result.isSuccess());
            assertEquals(OperationFailureReason.CONFLICT, result.getReason());
        }

        @DisplayName("Success")
        @Test
        void success() {
            AddCommentServiceRequest request = new AddCommentServiceRequest()
                    .setRating(4)
                    .setUsageTime("3-6")
                    .setProductId("product-id")
                    .setCommentText("comment-text");

            Mockito.doReturn(Optional.of(new Product()))
                    .when(productRepository)
                    .getById(request.getProductId());

            var result = service.addComment(request, "user-id");

            Mockito.verify(commentRepository)
                    .save(commentArgumentCaptor.capture());
            var capturedComment = commentArgumentCaptor.getValue();
            assertTrue(result.isSuccess());
            assertThat(capturedComment.getCommentId()).isNotEmpty().isNotNull();
            assertEquals(Rating.FOUR, capturedComment.getRating());
            assertEquals(UsageTime.THREE_TO_SIX_MONTHS, capturedComment.getUsageTime());
            assertEquals("comment-text", capturedComment.getCommentText());
            assertEquals("user-id", capturedComment.getUserId());
            assertTrue(capturedComment.getCreatedAt().isBefore(Instant.now()));
            assertEquals("product-id", capturedComment.getProductId());
        }

    }


    @Nested
    class GetComments{
        @DisplayName("Wrong product id")
        @Test
        void getByWrongProductId(){
            String productId = "wrong-product-id";
            Mockito.doReturn(List.of())
                    .when(commentRepository)
                    .findAllByProductId(productId);

            var result = service.getCommentsByProductId(productId);
            assertThat(result).isEmpty();
        }

        @DisplayName("Success")
        @Test
        void success(){
            String productId = "product-id";
            Comment c1 = new Comment();
            Comment c2 = new Comment();
            Mockito.doReturn(List.of(c1,c2))
                    .when(commentRepository)
                    .findAllByProductId(productId);

            var result = service.getCommentsByProductId(productId);
            assertThat(result).contains(c1);
            assertThat(result).contains(c2);
        }
    }

    @Nested
    class DeleteComment{

        @DisplayName("Non exist comment")
        @Test
        void nonExistComment(){

            var deleteRequest = new DeleteCommentServiceRequest()
                    .setCommentId("comment-id");

            Mockito.doReturn(Optional.empty())
                    .when(commentRepository)
                    .getByCommentId(deleteRequest.getCommentId());

            var result = service.deleteComment(deleteRequest,"user-id", UserRole.ROLE_USER);
            assertFalse(result.isSuccess());
            assertEquals(OperationFailureReason.NOT_FOUND,result.getReason());

        }
        @DisplayName("Unauthorized user")
        @Test
        void unauthorizedUser(){
            var deleteRequest = new DeleteCommentServiceRequest()
                    .setCommentId("comment-id");

            Comment comment = new Comment()
                    .setUserId("user-id")
                    .setCommentText("comment-text");

            Mockito.doReturn(Optional.of(comment))
                    .when(commentRepository)
                    .getByCommentId(deleteRequest.getCommentId());

            var result = service.deleteComment(deleteRequest,"unauthorized-user-id",UserRole.ROLE_USER);
            assertFalse(result.isSuccess());
            assertEquals(OperationFailureReason.UNAUTHORIZED,result.getReason());
        }

        @DisplayName("Success with Admin Role")
        @Test
        void successWithAdminRole(){
            var deleteRequest = new DeleteCommentServiceRequest()
                    .setCommentId("comment-id");

            Comment comment = new Comment()
                    .setUserId("user-id")
                    .setCommentText("comment-text");

            Mockito.doReturn(Optional.of(comment))
                    .when(commentRepository)
                    .getByCommentId(deleteRequest.getCommentId());

            var result = service.deleteComment(deleteRequest,"wrong-user-id",UserRole.ROLE_ADMIN);
            assertTrue(result.isSuccess());

            Mockito.verify(commentRepository)
                    .deleteById(deleteRequest.getCommentId());

            Mockito.verify(productService)
                    .reduceProductRating(comment);

        }

        @DisplayName("Success")
        @Test
        void success(){
            var deleteRequest = new DeleteCommentServiceRequest()
                    .setCommentId("comment-id");

            Comment comment = new Comment()
                    .setUserId("user-id")
                    .setCommentText("comment-text");

            Mockito.doReturn(Optional.of(comment))
                    .when(commentRepository)
                    .getByCommentId(deleteRequest.getCommentId());

            var result = service.deleteComment(deleteRequest,"user-id",UserRole.ROLE_USER);
            assertTrue(result.isSuccess());

            Mockito.verify(commentRepository)
                    .deleteById(deleteRequest.getCommentId());

            Mockito.verify(productService)
                    .reduceProductRating(comment);

        }
    }
}