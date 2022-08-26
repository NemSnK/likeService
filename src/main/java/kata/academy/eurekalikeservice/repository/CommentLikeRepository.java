package kata.academy.eurekalikeservice.repository;

import kata.academy.eurekalikeservice.model.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    boolean existsByIdAndCommentIdAndUserId(Long commentLikeId, Long commentId, Long userId);
}
