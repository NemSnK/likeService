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
public class PostLikeServiceImp implements PostLikeService {

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
    public void deletePostLikeByPostLikeId(Long postLikeId) {
        postLikeRepository.deleteById(postLikeId);
    }


    @Transactional(readOnly = true)
    @Override
    public boolean existsByIdAndUserIdAndPostId(Long postLikeId, Long userId, Long postId) {
        return postLikeRepository.existsByIdAndUserIdAndPostId(postLikeId, userId, postId);
    }
}
