package terrabuio.heitor.abysslogv2.internal.recurso.dto.response;

public record RecursoResponse(
        Long id,
        String nome,
        Double quantidade,
        Long idExpedicao
) {}