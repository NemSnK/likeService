package kata.academy.eurekalikeservice.model.dto.request.postLike;

import javax.validation.constraints.NotNull;

public record PostLikePersistRequestDto (
    @NotNull Boolean positive) {

}
