package kata.academy.eurekalikeservice.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.Positive;

//TODO добавить валидацию в PostLikeRestController::addPostLike на существование поста (existsByPostId)
@FeignClient("eureka-content-service")
public interface ContentServiceFeignClient {
    @GetMapping("/api/internal/v1/posts/{postId}/exists")
    boolean existsByPostId(@PathVariable @Positive Long postId);
}


