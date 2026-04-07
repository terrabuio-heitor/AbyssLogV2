package terrabuio.heitor.abysslogv2.internal.expedicao.dto.response;

public record ExpedicaoResponse(
        Long id,
        String nome,
        String nomeNavio,
        String capitao,
        java.util.Date dataInicio,
        String status
) {}