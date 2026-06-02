package terrabuio.heitor.abysslogv2.internal.navio.mapper;

import terrabuio.heitor.abysslogv2.internal.navio.domain.Navio;

import terrabuio.heitor.abysslogv2.internal.navio.domain.TipoNavio;
import terrabuio.heitor.abysslogv2.internal.navio.dto.request.NavioRequest;
import terrabuio.heitor.abysslogv2.internal.navio.dto.response.NavioResponse;


public class NavioMapper {
    public static Navio toEntity(NavioRequest request, TipoNavio tipoNavio) {
        Navio navio = Navio.criarNovoNavio(
                request.nome(),
                tipoNavio,
                request.qualidade(),
                request.anoFabricacao()
        );
        navio.setDefesa(request.defesa());

        // Mantemos o campo string legado sincronizado para não quebrar consultas antigas de imediato
        navio.setTipo(tipoNavio.getNome());
        navio.calcularAtributosIniciais();
        return navio;
    }
    public static NavioResponse toResponse(Navio navio) {
        return new NavioResponse(
                navio.getId(),
                navio.getNome(),
                navio.getTipo(),
                navio.getCapacidadeTripulacao(),
                navio.getCapacidadeCarga(),
                navio.getVelocidade(),
                navio.getResistenciaMaxima(),
                navio.getAnoFabricacao(),
                navio.getStatus().getDescricao()
        );
    }
}
