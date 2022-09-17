package kata.academy.eurekalikeservice.service.impl;

import kata.academy.eurekalikeservice.model.entity.CommentLike;
import kata.academy.eurekalikeservice.repository.CommentLikeRepository;
import kata.academy.eurekalikeservice.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentLikeServiceImpl implements CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    @Override
    public CommentLike addCommentLike(CommentLike commentLike) {
        return commentLikeRepository.save(commentLike);
    }

    @Override
    public CommentLike updateCommentLike(CommentLike commentLike) {
        return commentLikeRepository.save(commentLike);
    }

    @Override
    public void deleteById(Long commentLikeId) {
        commentLikeRepository.deleteById(commentLikeId);
    }

    @Override
    public void deleteByCommentId(Long commentId) {
        List<Long> commentLikeIds = commentLikeRepository.findAllIdsByCommentId(commentId);
        commentLikeRepository.deleteAllByIdInBatch(commentLikeIds);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByIdAndCommentIdAndUserId(Long commentLikeId, Long commentId, Long userId) {
        return commentLikeRepository.existsByIdAndCommentIdAndUserId(commentLikeId, commentId, userId);
    }

    @Override
    public void deleteAllByCommentIds(List<Long> commentIds) {
        List<Long> commentLikeIds = commentLikeRepository.findAllIdsByCommentIds(commentIds);
        commentLikeRepository.deleteAllByIdInBatch(commentLikeIds);
    }

    @Override
    public boolean existsByCommentIdAndUserId(Long commentId, Long userId) {
        return commentLikeRepository.existsByCommentIdAndUserId(commentId,userId);
    }
}
