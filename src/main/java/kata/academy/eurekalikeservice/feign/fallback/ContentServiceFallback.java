package kata.academy.eurekalikeservice.feign.fallback;

import kata.academy.eurekalikeservice.feign.ContentServiceFeignClient;
import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.http.ResponseEntity;

record ContentServiceFallback(Throwable cause) implements ContentServiceFeignClient {

    @Override
    public ResponseEntity<Boolean> existsByCommentId(Long commentId) {
        throw new NoFallbackAvailableException("Сервис временно недоступен. Причина -> %s".formatted(cause.getMessage()), cause);
    }

    @Override
    public ResponseEntity<Boolean> existsByPostId(Long postId) {
        throw new NoFallbackAvailableException("Сервис временно недоступен. Причина -> %s".formatted(cause.getMessage()), cause);
    }
}
