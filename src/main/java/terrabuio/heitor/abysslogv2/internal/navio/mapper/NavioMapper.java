package terrabuio.heitor.abysslogv2.internal.navio.mapper;

import terrabuio.heitor.abysslogv2.internal.navio.domain.Navio;
import terrabuio.heitor.abysslogv2.internal.navio.dto.request.NavioRequest;
import terrabuio.heitor.abysslogv2.internal.navio.dto.response.NavioResponse;


public class NavioMapper {
    public static Navio toEntity(NavioRequest request) {
        Navio navio = new Navio();
        navio.setNome(request.nome());

        navio.setTipo(request.tipo());
        navio.setAnoFabricacao(request.anoFabricacao());

        navio.setCapacidadeTripulacao(request.capacidadeTripulacao());
        navio.setCapacidadeCarga(request.capacidadeCarga());
        navio.setVelocidade(request.velocidade());
        navio.setResistencia(request.resistencia());

        navio.setStatus("DISPONIVEL");
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
                navio.getResistencia(),
                navio.getAnoFabricacao(),
                navio.getStatus()
        );
    }
}
