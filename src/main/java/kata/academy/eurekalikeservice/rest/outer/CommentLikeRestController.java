package kata.academy.eurekalikeservice.rest.outer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kata.academy.eurekalikeservice.api.Response;
import kata.academy.eurekalikeservice.model.converter.CommentLikeMapper;
import kata.academy.eurekalikeservice.model.dto.CommentLikePersistRequestDto;
import kata.academy.eurekalikeservice.model.dto.CommentLikeUpdateRequestDto;
import kata.academy.eurekalikeservice.model.entity.CommentLike;
import kata.academy.eurekalikeservice.service.entity.CommentLikeService;
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
import java.util.Optional;


@Tag(name = "CommentLikeRestController", description = "CRUD операции над CommentLike")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/comments")
public class CommentLikeRestController {

    private  final CommentLikeService commentLikeService;


    @Operation(summary = "Создание нового commentLike")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый commentLike успешно создан"),
            @ApiResponse(responseCode = "400", description = "Комментарий для commentLike не найден")
    })
    @PostMapping("/{commentId}/comment-likes")
    Response<CommentLike> addCommentLike(@PathVariable @Positive Long commentId,
                                         @RequestBody @Valid CommentLikePersistRequestDto dto,
                                         @RequestParam @Positive Long userId) {
        ApiValidationUtil.requireTrue(commentLikeService.existsByCommentId(commentId), String.format("Comment by id %d not found", commentId));
        return Response.ok(commentLikeService.addCommentLike(CommentLikeMapper.toEntity(dto), commentId, userId));
    }


    @Operation(summary = "Эндпоинт для обновление существующего commentLike")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CommentLike успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "CommentLike не найден")
    })
    @PutMapping("/{commentId}/comment-likes/{commentLikeId}")
    Response<CommentLike> updateCommentLike(@PathVariable @Positive Long commentId,
                                            @PathVariable @Positive Long commentLikeId,
                                            @RequestBody @Valid CommentLikeUpdateRequestDto dto,
                                            @RequestParam @Positive Long userId) {
        Optional<CommentLike> commentLike = commentLikeService.findByCommentIdAndIdAndUserId(commentId, commentLikeId, userId);
        ApiValidationUtil.requireTrue(commentLike.isPresent(), String.format("CommentLike by id %d not found", commentLikeId));
        return Response.ok(commentLikeService.updateCommentLike(CommentLikeMapper.toEntity(commentLike.get(), dto)));

    }


    @Operation(summary = "Удаление commentLike")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CommentLike успешно удален"),
            @ApiResponse(responseCode = "400", description = "CommentLike не найден")
    })
    @DeleteMapping("/{commentId}/comment-likes/{commentLikeId}")
    Response<Void> deleteCommentLike(@PathVariable @Positive Long commentId,
                                     @PathVariable @Positive Long commentLikeId,
                                     @RequestParam @Positive Long userId) {
        Optional<CommentLike> commentLike = commentLikeService.findByCommentIdAndIdAndUserId(commentId, commentLikeId, userId);
        ApiValidationUtil.requireTrue(commentLike.isPresent(), String.format("CommentLike by id %d not found", commentLikeId));
        commentLikeService.deleteCommentLike(commentLike.get());
        return Response.ok();
    }
}
