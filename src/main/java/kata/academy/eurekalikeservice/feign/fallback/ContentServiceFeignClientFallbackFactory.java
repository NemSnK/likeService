package kata.academy.eurekalikeservice.feign.fallback;


import kata.academy.eurekalikeservice.feign.client.ContentServiceFeignClient;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ContentServiceFeignClientFallbackFactory implements FallbackFactory<ContentServiceFeignClient> {
    @Override
    public ContentServiceFeignClient create(Throwable cause) {
        return new ContentServiceFeignClientFallback();
    }
}
