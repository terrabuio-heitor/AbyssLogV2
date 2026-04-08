package terrabuio.heitor.abysslogv2.internal.navio.dto.response;

public record NavioResponse(
        Long id,
        String nome,
        String tipo,
        Integer capacidadeTripulacao,
        Integer capacidadeCarga,
        Integer velocidade,
        Integer resistencia,
        Integer anoFabricacao,
        String status
) {}