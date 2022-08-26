package kata.academy.eurekalikeservice.model.dto;

import javax.validation.constraints.NotNull;

public record CommentLikeUpdateRequestDto(@NotNull Boolean positive) {
}
