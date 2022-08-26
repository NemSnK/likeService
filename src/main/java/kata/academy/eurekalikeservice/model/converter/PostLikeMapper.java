package kata.academy.eurekalikeservice.model.converter;

import kata.academy.eurekalikeservice.model.dto.request.postLike.PostLikePersistRequestDto;
import kata.academy.eurekalikeservice.model.dto.request.postLike.PostLikeUpdateRequestDto;
import kata.academy.eurekalikeservice.model.entity.PostLike;

public final class PostLikeMapper {

    public PostLikeMapper() {
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
