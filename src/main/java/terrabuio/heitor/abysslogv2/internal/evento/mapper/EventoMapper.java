package terrabuio.heitor.abysslogv2.internal.evento.mapper;

import terrabuio.heitor.abysslogv2.internal.evento.domain.Evento;
import terrabuio.heitor.abysslogv2.internal.evento.dto.request.EventoRequest;
import terrabuio.heitor.abysslogv2.internal.evento.dto.response.EventoResponse;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.monstro.domain.Monstro;

import java.util.List;

public class EventoMapper {
    public static Evento toEntity(EventoRequest request, Expedicao expedicao, List<Monstro> monstros) {
        Evento evento = new Evento();

        evento.setTipo(request.tipo());
        evento.setDescricao(request.descricao());
        evento.setData(request.data());
        evento.setExpedicao(expedicao); // vem do service

        // relação N:N com monstros
        if (monstros != null) {
            evento.setMonstros(monstros);
        }

        return evento;
    }

    public static EventoResponse toResponse(Evento evento) {
        List<String> nomesMonstros = evento.getMonstros() != null
                ? evento.getMonstros().stream()
                  .map(Monstro::getNome)
                  .toList()
                : List.of();

        return new EventoResponse(
                evento.getId(),
                evento.getTipo(),
                evento.getDescricao(),
                evento.getData(),
                evento.getExpedicao() != null ? evento.getExpedicao().getId() : null,
                nomesMonstros
        );
    }
}
