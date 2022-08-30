package kata.academy.eurekalikeservice.rest.inner;

import kata.academy.eurekalikeservice.service.CommentLikeService;
import kata.academy.eurekalikeservice.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/internal/v1/comments")
public class CommentLikeInternalRestController {

    private final CommentLikeService commentLikeService;

    @DeleteMapping("/{commentId}/comment-likes")
    public ResponseEntity<Void> deleteByCommentId(@PathVariable @Positive Long commentId) {
        commentLikeService.deleteByCommentId(commentId);
        return ResponseEntity.ok().build();
    }
}
