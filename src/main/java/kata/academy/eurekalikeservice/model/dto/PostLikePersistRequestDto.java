package kata.academy.eurekalikeservice.model.dto;

import javax.validation.constraints.NotNull;

public record PostLikePersistRequestDto(
        @NotNull Boolean positive) {
}
