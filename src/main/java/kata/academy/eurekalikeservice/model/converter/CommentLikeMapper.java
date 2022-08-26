package kata.academy.eurekalikeservice.model.converter;

import kata.academy.eurekalikeservice.model.dto.CommentLikePersistRequestDto;
import kata.academy.eurekalikeservice.model.dto.CommentLikeUpdateRequestDto;
import kata.academy.eurekalikeservice.model.entity.CommentLike;

public final class CommentLikeMapper {

    private CommentLikeMapper() {
    }

    public static CommentLike toEntity(CommentLikePersistRequestDto dto) {
        CommentLike commentLike = new CommentLike();
        commentLike.setPositive(dto.positive());
        return commentLike;
    }

    public static CommentLike toEntity(CommentLikeUpdateRequestDto dto) {
        CommentLike commentLike = new CommentLike();
        commentLike.setPositive(dto.positive());
        return commentLike;
    }
}
