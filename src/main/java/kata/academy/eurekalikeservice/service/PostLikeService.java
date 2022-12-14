package kata.academy.eurekalikeservice.service;

import kata.academy.eurekalikeservice.model.entity.PostLike;

import java.util.List;
import java.util.Optional;

public interface PostLikeService {

    PostLike addPostLike(PostLike postLike);

    PostLike updatePostLike(PostLike postLike);

    void delete(PostLike postLike);

    List<Long> getTopPostIdsByCount(Integer count);

    boolean existsByIdAndPostIdAndUserId(Long postLikeId, Long postId, Long userId);

    void deleteByPostId(Long postId);

    boolean existsByPostIdAndUserId(Long postId, Long userId);

    int countByPostIdAndPositive(Long postId, Boolean positive);

    Optional<PostLike> findByIdAndPostIdAndUserId(Long postLikeId, Long postId, Long userId);
}
