package kata.academy.eurekalikeservice.service.impl;

import kata.academy.eurekalikeservice.model.entity.PostLike;
import kata.academy.eurekalikeservice.repository.PostLikeRepository;
import kata.academy.eurekalikeservice.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    @Override
    public boolean existsByIdAndPostIdAndUserId(Long postLikeId, Long postId, Long userId) {
        return postLikeRepository.existsByIdAndPostIdAndUserId(postLikeId, postId, userId);
    }
    @Override
    public void deleteAllByPostId(Long postId){
        postLikeRepository.deleteAllByPostId(postId);
    }
}
