package kata.academy.eurekalikeservice.feign;

import feign.FeignException;
import kata.academy.eurekalikeservice.feign.fallback.ContentServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.Positive;

@FeignClient(value = "eureka-content-service", fallbackFactory = ContentServiceFallbackFactory.class)
public interface ContentServiceFeignClient {

    @GetMapping("/api/internal/v1/comments/{commentId}/exists")
    ResponseEntity<Boolean> existsByCommentId(@PathVariable @Positive Long commentId) throws FeignException;

    @GetMapping("/api/internal/v1/posts/{postId}/exists")
    ResponseEntity<Boolean> existsByPostId(@PathVariable @Positive Long postId);
}
