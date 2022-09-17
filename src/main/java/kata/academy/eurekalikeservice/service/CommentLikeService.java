package kata.academy.eurekalikeservice.service;

import kata.academy.eurekalikeservice.model.entity.CommentLike;

import java.util.List;

public interface CommentLikeService {

    CommentLike addCommentLike(CommentLike commentLike);

    CommentLike updateCommentLike(CommentLike commentLike);

    void deleteById(Long commentLikeId);

    void deleteByCommentId(Long commentId);

    boolean existsByIdAndCommentIdAndUserId(Long commentLikeId, Long commentId, Long userId);

    void deleteAllByCommentIds(List<Long> commentIds);

    boolean existsByCommentIdAndUserId(Long commentId, Long userId);
}
