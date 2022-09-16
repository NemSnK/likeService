package kata.academy.eurekalikeservice.service.impl;

import kata.academy.eurekalikeservice.model.entity.CommentLike;
import kata.academy.eurekalikeservice.repository.CommentLikeRepository;
import kata.academy.eurekalikeservice.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@CacheConfig(cacheNames = "comment-like-count")
@Transactional
@Service
public class CommentLikeServiceImpl implements CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    @CacheEvict(key = "#commentLike.commentId + '-' + #commentLike.positive")
    @Override
    public CommentLike addCommentLike(CommentLike commentLike) {
        return commentLikeRepository.save(commentLike);
    }

    @Caching(evict = {
            @CacheEvict(key = "#commentLike.commentId + '-' + true"),
            @CacheEvict(key = "#commentLike.commentId + '-' + false")
    })
    @Override
    public CommentLike updateCommentLike(CommentLike commentLike) {
        return commentLikeRepository.save(commentLike);
    }

    @CacheEvict(key = "#commentLike.commentId + '-' + #commentLike.positive")
    @Override
    public void delete(CommentLike commentLike) {
        commentLikeRepository.delete(commentLike);
    }

    @Caching(evict = {
            @CacheEvict(key = "#commentId + '-' + true"),
            @CacheEvict(key = "#commentId + '-' + false")
    })
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
        for (Long commentId : commentIds) {
            deleteByCommentId(commentId);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<CommentLike> findByIdAndCommentIdAndUserId(Long commentLikeId, Long commentId, Long userId) {
        return commentLikeRepository.findByIdAndCommentIdAndUserId(commentLikeId, commentId, userId);
    }

    @Cacheable(key = "#commentId + '-' + #positive", unless = "#result < 100")
    @Transactional(readOnly = true)
    @Override
    public int countByCommentIdAndPositive(Long commentId, Boolean positive) {
        return commentLikeRepository.countByCommentIdAndPositive(commentId, positive);
    }
}
