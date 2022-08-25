package kata.academy.eurekalikeservice.service;

import kata.academy.eurekalikeservice.model.entity.PostLike;

public interface PostLikeService {
    PostLike addPostLike(PostLike postLike);

    PostLike updatePostLike(PostLike postLike);

    void deletePostLikeByPostLikeId(Long postLikeId);

    boolean existsByIdAndUserIdAndPostId(Long postLikeId, Long userId, Long postId);
}
