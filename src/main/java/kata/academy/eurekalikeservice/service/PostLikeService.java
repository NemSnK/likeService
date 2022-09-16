package kata.academy.eurekalikeservice.service;

import kata.academy.eurekalikeservice.model.entity.PostLike;

import java.util.Optional;

public interface PostLikeService {

    PostLike addPostLike(PostLike postLike);

    PostLike updatePostLike(PostLike postLike);

    void delete(PostLike postLike);

    boolean existsByIdAndPostIdAndUserId(Long postLikeId, Long postId, Long userId);

    void deleteByPostId(Long postId);

    int countByPostIdAndPositive(Long postId, Boolean positive);

    Optional<PostLike> findByIdAndPostIdAndUserId(Long postLikeId, Long postId, Long userId);
}
