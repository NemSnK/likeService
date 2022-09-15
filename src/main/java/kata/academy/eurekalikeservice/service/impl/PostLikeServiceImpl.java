package kata.academy.eurekalikeservice.service.impl;

import kata.academy.eurekalikeservice.model.entity.PostLike;
import kata.academy.eurekalikeservice.repository.PostLikeRepository;
import kata.academy.eurekalikeservice.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@CacheConfig
@RequiredArgsConstructor
@Transactional
@Service
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;

    @Override
    @CacheEvict(value = "count", key = "#postLike.postId.toString() + #postLike.positive.toString()")
    public PostLike addPostLike(PostLike postLike) {
        return postLikeRepository.save(postLike);
    }

    @Override
    @CacheEvict(value = "count", key = "#postLike.postId.toString() + #postLike.positive.toString()")
    public PostLike updatePostLike(PostLike postLike) {
        return postLikeRepository.save(postLike);
    }

    @Override
    public void deleteById(Long postId) {
        postLikeRepository.deleteById(postId);
    }

    @Override
    public void deleteByPostId(Long postId) {
        List<Long> postLikeIds = postLikeRepository.findAllIdsByPostId(postId);
        postLikeRepository.deleteAllByIdInBatch(postLikeIds);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByIdAndPostIdAndUserId(Long postLikeId, Long postId, Long userId) {
        return postLikeRepository.existsByIdAndPostIdAndUserId(postLikeId, postId, userId);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "count", key = "#postId.toString() + #positive.toString()")
    @Override
    public Integer getPostLikeCount(Long postId, Boolean positive) {
        return postLikeRepository.countAllByPostIdAndPositive(postId, positive);
    }
}
