package kata.academy.eurekalikeservice.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "post_likes")
public class PostLike {

    @Id
    Long id;

    @NotNull
    @Column(nullable = false)
    Long postId;

    @NotNull
    @Column(nullable = false)
    Long userId;

    @NotNull
    @Column(nullable = false)
    Boolean positive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostLike postLike = (PostLike) o;
        return getId().equals(postLike.getId()) && getPostId().equals(postLike.getPostId()) && getUserId().equals(postLike.getUserId()) && getPositive().equals(postLike.getPositive());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPostId(), getUserId(), getPositive());
    }
}