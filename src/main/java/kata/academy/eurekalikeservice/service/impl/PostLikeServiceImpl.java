package kata.academy.eurekalikeservice.service.impl;

import kata.academy.eurekalikeservice.model.entity.PostLike;
import kata.academy.eurekalikeservice.repository.PostLikeRepository;
import kata.academy.eurekalikeservice.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;

    @Override
    public PostLike addPostLike(PostLike postLike) {
        return postLikeRepository.save(postLike);
    }

    @Override
    public PostLike updatePostLike(PostLike postLike) {
        return postLikeRepository.save(postLike);
    }

    @Override
    public void deleteById(Long postId) {
        postLikeRepository.deleteById(postId);
    }

    @Override
    public List<Long> getPostsByLikesAmount(Integer count) {
        return postLikeRepository.getPostsByLikesAmount(count).stream().limit(count).collect(Collectors.toList());
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
}
