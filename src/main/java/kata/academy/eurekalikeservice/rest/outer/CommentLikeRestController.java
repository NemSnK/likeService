package kata.academy.eurekalikeservice.rest.outer;

import kata.academy.eurekalikeservice.api.Response;
import kata.academy.eurekalikeservice.feign.ContentServiceFeignClient;
import kata.academy.eurekalikeservice.model.converter.CommentLikeMapper;
import kata.academy.eurekalikeservice.model.dto.CommentLikePersistRequestDto;
import kata.academy.eurekalikeservice.model.dto.CommentLikeUpdateRequestDto;
import kata.academy.eurekalikeservice.model.entity.CommentLike;
import kata.academy.eurekalikeservice.service.CommentLikeService;
import kata.academy.eurekalikeservice.util.ApiValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/comments")
public class CommentLikeRestController {

    private final CommentLikeService commentLikeService;
    private final ContentServiceFeignClient contentServiceFeignClient;

    @PostMapping("/{commentId}/comment-likes")
    public Response<CommentLike> addCommentLike(@RequestBody @Valid CommentLikePersistRequestDto dto,
                                                @PathVariable @Positive Long commentId,
                                                @RequestParam @Positive Long userId) {
        ApiValidationUtil.requireTrue(contentServiceFeignClient.existsByCommentId(commentId).getBody(), String.format("Комментарий с таким commentId %d не существует в базе данных", commentId));
        CommentLike commentLike = CommentLikeMapper.toEntity(dto);
        commentLike.setCommentId(commentId);
        commentLike.setUserId(userId);
        return Response.ok(commentLikeService.addCommentLike(commentLike));
    }

    @PutMapping("/{commentId}/comment-likes/{commentLikeId}")
    public Response<CommentLike> updateCommentLike(@RequestBody @Valid CommentLikeUpdateRequestDto dto,
                                                   @PathVariable @Positive Long commentId,
                                                   @PathVariable @Positive Long commentLikeId,
                                                   @RequestParam @Positive Long userId) {
        ApiValidationUtil.requireTrue(commentLikeService.existsByIdAndCommentIdAndUserId(commentLikeId, commentId, userId), String.format("Лайк комментария с commentLikeId %d, commentId %d, userId %d нет в базе данных", commentLikeId, commentId, userId));
        CommentLike commentLike = CommentLikeMapper.toEntity(dto);
        commentLike.setId(commentLikeId);
        commentLike.setCommentId(commentId);
        commentLike.setUserId(userId);
        return Response.ok(commentLikeService.updateCommentLike(commentLike));
    }

    @DeleteMapping("/{commentId}/comment-likes/{commentLikeId}")
    public Response<Void> deleteCommentLike(@PathVariable @Positive Long commentId,
                                            @PathVariable @Positive Long commentLikeId,
                                            @RequestParam @Positive Long userId) {
        ApiValidationUtil.requireTrue(commentLikeService.existsByIdAndCommentIdAndUserId(commentLikeId, commentId, userId), String.format("Лайк комментария с commentLikeId %d, commentId %d, userId %d нет в базе данных", commentLikeId, commentId, userId));
        commentLikeService.deleteById(commentLikeId);
        return Response.ok();
    }
}
