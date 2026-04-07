package terrabuio.heitor.abysslogv2.internal.monstro.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MonstroRequest(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        String nivelPerigo,
        String descricao
) {}
