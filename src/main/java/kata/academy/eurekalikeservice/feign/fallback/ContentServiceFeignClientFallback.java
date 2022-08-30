package kata.academy.eurekalikeservice.feign.fallback;

import kata.academy.eurekalikeservice.feign.client.ContentServiceFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
public class ContentServiceFeignClientFallback implements ContentServiceFeignClient {

    @Override
    public ResponseEntity<Boolean> existsByCommentId(Long commentId) {
        throw new NoFallbackAvailableException("Сервис временно недоступен", new RuntimeException());
    }

    @Override
    public ResponseEntity<Boolean> existsByPostId(Long postId) {
        throw new NoFallbackAvailableException("Сервис временно недоступен", new RuntimeException());
    }
}
