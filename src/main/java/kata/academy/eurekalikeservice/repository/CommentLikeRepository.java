package kata.academy.eurekalikeservice.repository;

import kata.academy.eurekalikeservice.model.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    boolean existsByIdAndCommentIdAndUserId(Long commentLikeId, Long commentId, Long userId);

    boolean existsByCommentIdAndUserId(Long commentId, Long userId);

    @Query("""
            SELECT cl.id
            FROM CommentLike cl
            WHERE cl.commentId = :commentId
                                """)
    List<Long> findAllIdsByCommentId(Long commentId);

    @Query("""
            SELECT cl.id
            FROM CommentLike cl
            WHERE cl.commentId IN :commentIds
                                """)
    List<Long> findAllIdsByCommentIds(List<Long> commentIds);
}
