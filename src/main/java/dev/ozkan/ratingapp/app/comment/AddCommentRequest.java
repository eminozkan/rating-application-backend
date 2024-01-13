package dev.ozkan.ratingapp.app.comment;

import dev.ozkan.ratingapp.core.comment.AddCommentServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AddCommentRequest(
        @NotNull
        @NotBlank
        String productId,

        @NotNull
        String commentText,

        @NotNull
        Integer rating,

        @NotBlank
        @NotNull
        String usageTime
) {
    AddCommentServiceRequest toServiceRequest() {
        return new AddCommentServiceRequest()
                .setProductId(productId)
                .setRating(rating)
                .setUsageTime(usageTime)
                .setCommentText(commentText);
    }
}
