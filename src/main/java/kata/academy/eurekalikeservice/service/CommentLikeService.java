package kata.academy.eurekalikeservice.service;

import kata.academy.eurekalikeservice.model.entity.CommentLike;

public interface CommentLikeService {

    CommentLike addCommentLike(CommentLike commentLike);

    CommentLike updateCommentLike(CommentLike commentLike);

    void deleteById(Long commentLikeId);

    boolean existsByIdAndCommentIdAndUserId(Long commentLikeId, Long commentId, Long userId);
}
