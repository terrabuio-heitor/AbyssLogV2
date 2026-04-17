package terrabuio.heitor.abysslogv2.internal.expedicao.mapper;

import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.dto.request.ExpedicaoRequest;
import terrabuio.heitor.abysslogv2.internal.expedicao.dto.response.ExpedicaoResponse;
import terrabuio.heitor.abysslogv2.internal.navio.domain.Navio;

public class ExpedicaoMapper {
    public static Expedicao toEntity(ExpedicaoRequest request, Navio navio) {
        Expedicao expedicao = new Expedicao();

        expedicao.setNome(request.nome());
        expedicao.setNavio(navio); // vem do service ou controller
        expedicao.setCapitao(request.capitao());
        expedicao.setDataInicio(request.dataInicio());
        return expedicao;
    }
    public static ExpedicaoResponse toResponse(Expedicao expedicao) {
        return new ExpedicaoResponse(
                expedicao.getId(),
                expedicao.getNome(),
                expedicao.getNavio() != null ? expedicao.getNavio().getNome() : null,
                expedicao.getCapitao(),
                expedicao.getDataInicio(),
                expedicao.getStatus().getDescricao()
        );
    }
}
