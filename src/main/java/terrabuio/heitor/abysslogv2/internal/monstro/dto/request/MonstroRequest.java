package terrabuio.heitor.abysslogv2.internal.monstro.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MonstroRequest(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotNull(message = "O nível de perigo é obrigatório")
        Integer nivelPerigo,
        String descricao
) {}
