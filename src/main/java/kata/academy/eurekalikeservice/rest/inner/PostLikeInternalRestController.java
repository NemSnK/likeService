package kata.academy.eurekalikeservice.rest.inner;

import kata.academy.eurekalikeservice.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/internal/v1/posts")
public class PostLikeInternalRestController {

    private final PostLikeService postLikeService;

    @DeleteMapping("/{postId}/post-likes")
    public ResponseEntity<Void> deleteByPostId(@PathVariable @Positive Long postId) {
        postLikeService.deleteByPostId(postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/post-likes")
    public ResponseEntity<List<Long>> getPostsByLikesAmount(@RequestParam(defaultValue = "100") @Positive Integer count) {
        return ResponseEntity.ok(postLikeService.getPostsByLikesAmount(count));
    }
}
