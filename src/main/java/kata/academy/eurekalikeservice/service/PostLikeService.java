package kata.academy.eurekalikeservice.service;

import kata.academy.eurekalikeservice.model.entity.PostLike;

<<<<<<< HEAD
import java.util.List;
=======
import java.util.Optional;
>>>>>>> 6295f3338493c7ef16d8969e27c23daafcd6ef35

public interface PostLikeService {

    PostLike addPostLike(PostLike postLike);

    PostLike updatePostLike(PostLike postLike);

    void delete(PostLike postLike);

    List<Long> getPostsByLikesAmount(Integer count);

    boolean existsByIdAndPostIdAndUserId(Long postLikeId, Long postId, Long userId);

    void deleteByPostId(Long postId);

    boolean existsByPostIdAndUserId(Long postId, Long userId);

    int countByPostIdAndPositive(Long postId, Boolean positive);

    Optional<PostLike> findByIdAndPostIdAndUserId(Long postLikeId, Long postId, Long userId);
}
