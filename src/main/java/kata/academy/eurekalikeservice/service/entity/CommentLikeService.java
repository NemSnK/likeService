package kata.academy.eurekalikeservice.service.entity;

import kata.academy.eurekalikeservice.model.entity.CommentLike;

import java.util.Optional;

public interface CommentLikeService {

    CommentLike addCommentLike(CommentLike commentLike, Long commentId, Long userId);

    CommentLike updateCommentLike(CommentLike commentLike);

    void deleteCommentLike(CommentLike commentLike);

    boolean existsByCommentId(Long commentId);

    Optional<CommentLike> findByCommentIdAndIdAndUserId(Long commentId, Long commentLikeId, Long UserId);
}
