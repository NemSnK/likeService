package kata.academy.eurekalikeservice.repository;

import kata.academy.eurekalikeservice.model.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    boolean existsByCommentId(Long commentId);
    Optional<CommentLike> findByCommentIdAndIdAndUserId(Long commentId, Long commentLikeId, Long UserId);
}
