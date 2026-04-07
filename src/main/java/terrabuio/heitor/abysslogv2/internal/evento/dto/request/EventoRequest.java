package terrabuio.heitor.abysslogv2.internal.evento.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record EventoRequest(
        @NotBlank(message = "O tipo é obrigatório")
        String tipo,
        @NotBlank(message = "A descrição é obrigatória")
        String descricao,
        //@NotNull(message = "A data é obrigatória")
        LocalDateTime data,

        @NotNull(message = "O ID da expedição é obrigatório")
        Long idExpedicao,
        List<Long> monstroId
){}
