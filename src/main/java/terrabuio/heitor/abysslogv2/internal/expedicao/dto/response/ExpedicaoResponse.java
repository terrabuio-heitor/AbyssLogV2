package terrabuio.heitor.abysslogv2.internal.expedicao.dto.response;

import java.time.LocalDate;

public record ExpedicaoResponse(
        Long id,
        String nome,
        String nomeNavio,
        String capitao,
        LocalDate dataInicio,
        String status
) {}