package terrabuio.heitor.abysslogv2.internal.recurso.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RecursoRequest(
        @NotBlank(message = "O nome do recurso é obrigatório")
        String nome,
        @NotNull(message = "A quantidade é obrigatória")
        Double quantidade,
        @NotNull(message = "O ID da expedição é obrigatório")
        Long idExpedicao
) {}
