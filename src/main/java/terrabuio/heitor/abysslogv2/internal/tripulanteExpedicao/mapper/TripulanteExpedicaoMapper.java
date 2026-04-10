package terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.mapper;

import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.tripulante.domain.Tripulante;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.domain.TripulanteExpedicao;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.dto.request.TripulanteExpedicaoRequest;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.dto.response.TripulanteExpedicaoResponse;

public class TripulanteExpedicaoMapper {
    public static TripulanteExpedicao toEntity(TripulanteExpedicaoRequest request, Tripulante tripulante, Expedicao expedicao) {
        TripulanteExpedicao triEx = new TripulanteExpedicao();
        triEx.setTripulante(tripulante);
        triEx.setExpedicao(expedicao);
        triEx.setDataEntrada(request.dataEntrada());
        triEx.setAtivo(true);
        return triEx;
    }
    public static TripulanteExpedicaoResponse toResponse(TripulanteExpedicao triEx) {
        return new TripulanteExpedicaoResponse(
                triEx.getId(),
                triEx.getTripulante().getId(),
                triEx.getTripulante().getNome(),
                triEx.getTripulante().getCargo(),
                triEx.getExpedicao().getId(),
                triEx.getExpedicao().getNome(),
                triEx.getDataEntrada(),
                triEx.getDataSaida(),
                triEx.getAtivo()
        );
    }
}
