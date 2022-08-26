package kata.academy.eurekalikeservice.rest.inner;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kata.academy.eurekalikeservice.api.Response;
import kata.academy.eurekalikeservice.model.converter.PostLikeMapper;
import kata.academy.eurekalikeservice.model.dto.request.postLike.PostLikePersistRequestDto;
import kata.academy.eurekalikeservice.model.dto.request.postLike.PostLikeUpdateRequestDto;
import kata.academy.eurekalikeservice.model.entity.PostLike;
import kata.academy.eurekalikeservice.service.PostLikeService;
import kata.academy.eurekalikeservice.util.ApiValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@Tag(name = "PostLikeRestController", description = "Контроллер для добавления лайков к посту")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostLikeRestController {

    private final PostLikeService postLikeService;

    @Operation(summary = "Добавление лайка посту")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Лайк посту успешно поставлен")
    })
    @PostMapping("/{postId}/post-likes")
    public Response<PostLike> addPostLike(@PathVariable @Positive Long postId,
                                          @RequestBody PostLikePersistRequestDto dto,
                                          @RequestParam("userId") @Positive Long userId) {
        PostLike postLike = PostLikeMapper.toEntity(dto);
        postLike.setPostId(postId);
        postLike.setUserId(userId);

        return Response.ok(postLikeService.addPostLike(postLike));
    }

    @PutMapping("/{postId}/post-likes/{postLikeId}")
    public Response<PostLike> updatePostLike(@PathVariable @Positive Long postId,
                                             @PathVariable @Positive Long postLikeId,
                                             @RequestBody PostLikeUpdateRequestDto dto,
                                             @RequestParam("userId") @Positive Long userId) {
        ApiValidationUtil.requireTrue(postLikeService.existsByIdAndUserIdAndPostId(postLikeId, userId, postId), String.format("Лайк с postLikeId %d и userId %d и postId %d нет в базе данных", postLikeId, userId, postId));
        PostLike postLike = PostLikeMapper.toEntity(dto);
        postLike.setId(postLikeId);
        postLike.setPostId(postId);
        postLike.setUserId(userId);

        return Response.ok(postLikeService.updatePostLike(postLike));
    }

    @DeleteMapping("/{postId}/post-likes/{postLikeId}")
    public Response<Void> deletePostLike(@PathVariable @Positive Long postId,
                                         @PathVariable @Positive Long postLikeId,
                                         @RequestParam("userId") @Positive Long userId) {
        ApiValidationUtil.requireTrue(postLikeService.existsByIdAndUserIdAndPostId(postLikeId, userId, postId), String.format("Лайк с postLikeId %d и userId %d и postId %d нет в базе данных", postLikeId, userId, postId));
        postLikeService.deletePostLikeByPostLikeId(postLikeId);
        return Response.ok();
    }

}
