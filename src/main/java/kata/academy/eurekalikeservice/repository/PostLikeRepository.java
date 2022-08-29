package kata.academy.eurekalikeservice.repository;

import kata.academy.eurekalikeservice.model.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByIdAndPostIdAndUserId(Long postLikeId, Long postId, Long userId);

    void deleteAllByPostId(Long postId);
}
