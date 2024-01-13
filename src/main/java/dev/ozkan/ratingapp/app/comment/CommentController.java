package dev.ozkan.ratingapp.app.comment;

import dev.ozkan.ratingapp.config.UserSession;
import dev.ozkan.ratingapp.core.comment.CommentService;
import dev.ozkan.ratingapp.core.model.comment.Comment;
import dev.ozkan.ratingapp.support.response.CommentResponse;
import dev.ozkan.ratingapp.support.response.ResponseMessage;
import dev.ozkan.ratingapp.support.resulthandler.BusinessResultHandler;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comments")
@Validated
public class CommentController {
    private final CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<?> addComment(UserSession session, @RequestBody @Valid AddCommentRequest request){
        var result = commentService.addComment(request.toServiceRequest(),session.id());
        if (!result.isSuccess()){
            return BusinessResultHandler.handleFailureReason(result.getReason(), result.getMessage());
        }
        return ResponseEntity.ok(new ResponseMessage().setMessage(result.getMessage()));
    }

    @GetMapping()
    public ResponseEntity<?> getCommentsByProductIdI(@RequestBody GetCommentsRequest request){
        List<Comment> result = commentService.getCommentsByProductId(request.productId());
        List<CommentResponse> commentResponseList = convertCommentsToCommentResponse(result);
        return ResponseEntity.ok(commentResponseList);
    }

    private List<CommentResponse> convertCommentsToCommentResponse(List<Comment> result) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment c : result){
            CommentResponse response = new CommentResponse()
                    .setCommentText(c.getCommentText())
                    .setUsageTime(c.getUsageTime())
                    .setRating(c.getRating())
                    .setCreatedAt(c.getCreatedAt());
            commentResponses.add(response);
        }
        return commentResponses;
    }
}
