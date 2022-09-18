package kata.academy.eurekalikeservice.rest.outer;

import kata.academy.eurekalikeservice.api.Response;
import kata.academy.eurekalikeservice.feign.ContentServiceFeignClient;
import kata.academy.eurekalikeservice.model.converter.PostLikeMapper;
import kata.academy.eurekalikeservice.model.dto.PostLikePersistRequestDto;
import kata.academy.eurekalikeservice.model.dto.PostLikeUpdateRequestDto;
import kata.academy.eurekalikeservice.model.entity.PostLike;
import kata.academy.eurekalikeservice.service.PostLikeService;
import kata.academy.eurekalikeservice.util.ApiValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Optional;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/posts")
public class PostLikeRestController {

    private final PostLikeService postLikeService;
    private final ContentServiceFeignClient contentServiceFeignClient;

    @PostMapping("/{postId}/post-likes")
    public Response<PostLike> addPostLike(@RequestBody @Valid PostLikePersistRequestDto dto,
                                          @PathVariable @Positive Long postId,
                                          @RequestParam @Positive Long userId) {
        ApiValidationUtil.requireTrue(contentServiceFeignClient.existsByPostId(postId).getBody(),
                String.format("Пост с таким postId %d не существует в базе данных", postId));
        ApiValidationUtil.requireFalse(postLikeService.existsByPostIdAndUserId(postId,userId),
                String.format("Пользователь userId %d уже лайкнул пост postId %d",userId,postId));
        PostLike postLike = PostLikeMapper.toEntity(dto);
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        return Response.ok(postLikeService.addPostLike(postLike));
    }

    @PutMapping("/{postId}/post-likes/{postLikeId}")
    public Response<PostLike> updatePostLike(@RequestBody @Valid PostLikeUpdateRequestDto dto,
                                             @PathVariable @Positive Long postId,
                                             @PathVariable @Positive Long postLikeId,
                                             @RequestParam @Positive Long userId) {
        ApiValidationUtil.requireTrue(postLikeService.existsByIdAndPostIdAndUserId(postLikeId, postId, userId), String.format("Лайк поста с postLikeId %d, postId %d, userId %d нет в базе данных", postLikeId, postId, userId));
        PostLike postLike = PostLikeMapper.toEntity(dto);
        postLike.setId(postLikeId);
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        return Response.ok(postLikeService.updatePostLike(postLike));
    }

    @DeleteMapping("/{postId}/post-likes/{postLikeId}")
    public Response<Void> deletePostLike(@PathVariable @Positive Long postId,
                                         @PathVariable @Positive Long postLikeId,
                                         @RequestParam @Positive Long userId) {
        Optional<PostLike> postLikeOptional = postLikeService.findByIdAndPostIdAndUserId(postLikeId, postId, userId);
        ApiValidationUtil.requireTrue(postLikeOptional.isPresent(), String.format("Лайк поста с postLikeId %d, postId %d, userId %d нет в базе данных", postLikeId, postId, userId));
        postLikeService.delete(postLikeOptional.get());
        return Response.ok();
    }

    @GetMapping("/{postId}/post-likes/count")
    public Response<Integer> getPostLikeCount(@PathVariable @Positive Long postId,
                                              @RequestParam Boolean positive,
                                              @RequestParam @Positive Long userId) {
        ApiValidationUtil.requireTrue(contentServiceFeignClient.existsByPostId(postId).getBody(), String.format("Пост с таким postId %d не существует в базе данных", postId));
        return Response.ok(postLikeService.countByPostIdAndPositive(postId, positive));
    }
}
