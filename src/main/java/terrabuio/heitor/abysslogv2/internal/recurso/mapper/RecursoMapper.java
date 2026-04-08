package terrabuio.heitor.abysslogv2.internal.recurso.mapper;

import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.recurso.domain.Recurso;
import terrabuio.heitor.abysslogv2.internal.recurso.dto.request.RecursoRequest;
import terrabuio.heitor.abysslogv2.internal.recurso.dto.response.RecursoResponse;

public class RecursoMapper {
    public static Recurso toEntity(RecursoRequest request, Expedicao expedicao) {
        Recurso recurso = new Recurso();
        recurso.setNome(request.nome());
        recurso.setQuantidade(request.quantidade());
        recurso.setExpedicao(expedicao);
        return recurso;
    }

    public static RecursoResponse toResponse(Recurso recurso) {
        return new RecursoResponse(
                recurso.getId(),
                recurso.getNome(),
                recurso.getQuantidade(),
                recurso.getExpedicao() != null ? recurso.getExpedicao().getNome() : "Sem Expedição cadastrada"
        );
    }
}
