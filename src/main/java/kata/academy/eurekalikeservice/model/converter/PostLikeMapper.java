package kata.academy.eurekalikeservice.model.converter;

import kata.academy.eurekalikeservice.model.dto.PostLikePersistRequestDto;
import kata.academy.eurekalikeservice.model.dto.PostLikeUpdateRequestDto;
import kata.academy.eurekalikeservice.model.entity.PostLike;

public final class PostLikeMapper {

    private PostLikeMapper() {
    }

    public static PostLike toEntity(PostLikePersistRequestDto dto) {
        PostLike postLike = new PostLike();
        postLike.setPositive(dto.positive());
        return postLike;
    }

    public static PostLike toEntity(PostLikeUpdateRequestDto dto) {
        PostLike postLike = new PostLike();
        postLike.setPositive(dto.positive());
        return postLike;
    }
}
