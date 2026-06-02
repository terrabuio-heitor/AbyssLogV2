package terrabuio.heitor.abysslogv2.internal.navio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import terrabuio.heitor.abysslogv2.internal.navio.domain.Navio.QualidadeNavio;

public record NavioRequest(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotNull(message = "O ID do tipo de navio é obrigatório")
        Long tipoNavioId,

        @NotNull(message = "A qualidade do navio é obrigatória")
        QualidadeNavio qualidade,

        @NotNull(message = "A defesa base do navio é obrigatória")
        Float defesa,

        @NotNull(message = "O ano de fabricação é obrigatório")
        Integer anoFabricacao
) {}