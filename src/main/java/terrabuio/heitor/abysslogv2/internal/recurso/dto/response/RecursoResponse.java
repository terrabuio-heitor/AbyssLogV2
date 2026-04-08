package terrabuio.heitor.abysslogv2.internal.recurso.dto.response;

public record RecursoResponse(
        Long id,
        String nome,
        Integer quantidade,
        Long idExpedicao
) {}