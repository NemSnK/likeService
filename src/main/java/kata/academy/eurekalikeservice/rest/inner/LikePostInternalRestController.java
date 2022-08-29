package kata.academy.eurekalikeservice.rest.inner;

import kata.academy.eurekalikeservice.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;


@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/internal/v1/likes")
public class LikePostInternalRestController {
    private final PostLikeService postLikeService;

    @GetMapping("/{postId}/delete")
    public void deleteAllByPostId(@PathVariable @Positive Long postId) {
        postLikeService.deleteAllByPostId(postId);
    }
}