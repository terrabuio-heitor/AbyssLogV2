package terrabuio.heitor.abysslogv2.internal.navio.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NavioRequest(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotBlank(message = "O tipo é obrigatório")
        String tipo,

        @NotNull(message = "A capacidade de tripulação é obrigatória")
        @Min(value = 1, message = "A tripulação deve ser de no mínimo 1 pessoa")
        Integer capacidadeTripulacao,

        @NotNull(message = "A capacidade de carga é obrigatória")
        Integer capacidadeCarga,

        @NotNull(message = "A velocidade é obrigatória")
        @Min(1)
        Integer velocidade,

        @NotNull(message = "A resistência é obrigatória")
        @Min(1)
        Integer resistencia,

        @NotNull(message = "O ano de fabricação é obrigatório")
        Integer anoFabricacao
        //String status
) {}