package kata.academy.eurekalikeservice.service;

import kata.academy.eurekalikeservice.model.entity.PostLike;

public interface PostLikeService {

    PostLike addPostLike(PostLike postLike);

    PostLike updatePostLike(PostLike postLike);

    void deleteById(Long postId);

    boolean existsByIdAndPostIdAndUserId(Long postLikeId, Long postId, Long userId);

    void deleteByPostId(Long postId);
    Integer getPostLikeCount(Long postId, Boolean positive);
}
