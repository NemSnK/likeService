package kata.academy.eurekalikeservice.repository;

import kata.academy.eurekalikeservice.model.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByIdAndPostIdAndUserId(Long postLikeId, Long postId, Long userId);

    @Query("""
            SELECT pl.id
            FROM PostLike pl
            WHERE pl.postId = :postId
                                """)
    List<Long> findAllIdsByPostId(Long postId);
}
