package terrabuio.heitor.abysslogv2.internal.tripulante.mapper;


import terrabuio.heitor.abysslogv2.internal.tripulante.domain.Tripulante;
import terrabuio.heitor.abysslogv2.internal.tripulante.dto.request.TripulanteRequest;
import terrabuio.heitor.abysslogv2.internal.tripulante.dto.response.TripulanteResponse;

public class TripulanteMapper {
    public static Tripulante toEntity(TripulanteRequest request) {
        Tripulante tripulante = new Tripulante();
        tripulante.setNome(request.nome());
        tripulante.setCargo(request.cargo());
        return tripulante;
    }

    public static TripulanteResponse toResponse(Tripulante tripulante){
        return new TripulanteResponse(
                tripulante.getId(),
                tripulante.getNome(),
                tripulante.getCargo()
        );
    }
}
