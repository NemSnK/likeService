package kata.academy.eurekalikeservice.repository;

import kata.academy.eurekalikeservice.model.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByIdAndPostIdAndUserId(Long postLikeId, Long postId, Long userId);

    @Query("""
            SELECT pl.id
            FROM PostLike pl
            WHERE pl.postId = :postId
                                """)
    List<Long> findAllIdsByPostId(Long postId);

    @Query(
            """
                    SELECT pl.postId
                    FROM PostLike pl
                    WHERE pl.positive = true
                    GROUP BY pl.postId
                    ORDER BY count (pl.id)
                    DESC
                    """
    )
    List<Long> getPostsByLikesAmount(Integer count);

    int countByPostIdAndPositive(Long postId, Boolean positive);

    Optional<PostLike> findByIdAndPostIdAndUserId(Long postLikeId, Long postId, Long userId);
}
