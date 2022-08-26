package kata.academy.eurekalikeservice.service.impl;

import kata.academy.eurekalikeservice.model.entity.CommentLike;
import kata.academy.eurekalikeservice.repository.CommentLikeRepository;
import kata.academy.eurekalikeservice.service.entity.CommentLikeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class CommentLikeServiceImpl implements CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    public CommentLikeServiceImpl(CommentLikeRepository commentLikeRepository) {
        this.commentLikeRepository = commentLikeRepository;
    }
    @Override
    public CommentLike addCommentLike(CommentLike commentLike, Long commentId, Long userId) {
        commentLike.setCommentId(commentId);
        commentLike.setUserId(userId);
        return commentLikeRepository.save(commentLike);
    }

    @Override
    public CommentLike updateCommentLike(CommentLike commentLike) {
        commentLike.setPositive(commentLike.getPositive());
        return commentLikeRepository.save(commentLike);
    }

    @Override
    public void deleteCommentLike(CommentLike commentLike) {
        commentLikeRepository.delete(commentLike);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCommentId(Long commentId) {
        return commentLikeRepository.existsByCommentId(commentId);
    }

    @Override
    public Optional<CommentLike> findByCommentIdAndIdAndUserId(Long commentId, Long commentLikeId, Long UserId) {
        return commentLikeRepository.findByCommentIdAndIdAndUserId(commentId, commentLikeId, UserId);
    }
}
