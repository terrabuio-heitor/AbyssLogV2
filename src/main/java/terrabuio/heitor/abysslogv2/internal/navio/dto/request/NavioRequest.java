package terrabuio.heitor.abysslogv2.internal.navio.dto.request;

import jakarta.validation.constraints.NotBlank;

public record NavioRequest(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        String tipo,
        Integer capacidadeTripulacao,
        Double capacidadeCarga,
        Integer velocidade,
        Integer resistencia,
        Integer anoFabricacao,
        String status
) {}