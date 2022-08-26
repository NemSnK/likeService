package kata.academy.eurekalikeservice.model.converter;

import kata.academy.eurekalikeservice.model.dto.CommentLikePersistRequestDto;
import kata.academy.eurekalikeservice.model.dto.CommentLikeUpdateRequestDto;
import kata.academy.eurekalikeservice.model.entity.CommentLike;

public class CommentLikeMapper {

    public static CommentLike toEntity(CommentLikePersistRequestDto dto) {
        CommentLike commentLike = new CommentLike();
        commentLike.setPositive(dto.positive());
        return commentLike;
    }

    public static CommentLike toEntity(CommentLike commentLike, CommentLikeUpdateRequestDto dto) {
        commentLike.setPositive(dto.positive());
        return commentLike;
    }
}
