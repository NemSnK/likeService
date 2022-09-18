package kata.academy.eurekalikeservice.service;

import kata.academy.eurekalikeservice.model.entity.PostLike;

import java.util.List;

public interface PostLikeService {

    PostLike addPostLike(PostLike postLike);

    PostLike updatePostLike(PostLike postLike);

    void deleteById(Long postId);

    List<Long> getPostsByLikesAmount(Integer count);

    boolean existsByIdAndPostIdAndUserId(Long postLikeId, Long postId, Long userId);

    void deleteByPostId(Long postId);
}
