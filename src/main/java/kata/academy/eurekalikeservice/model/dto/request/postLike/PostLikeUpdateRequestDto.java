package kata.academy.eurekalikeservice.model.dto.request.postLike;

import javax.validation.constraints.NotNull;

public record PostLikeUpdateRequestDto (
    @NotNull Boolean positive) {
}
