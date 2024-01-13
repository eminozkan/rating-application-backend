package dev.ozkan.ratingapp.app.comment;

import dev.ozkan.ratingapp.core.comment.CommentService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
    private final CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
}
