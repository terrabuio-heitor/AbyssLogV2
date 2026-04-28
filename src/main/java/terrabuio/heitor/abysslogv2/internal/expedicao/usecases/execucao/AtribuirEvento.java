package terrabuio.heitor.abysslogv2.internal.expedicao.usecases.execucao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.internal.evento.domain.Evento;
import terrabuio.heitor.abysslogv2.internal.evento.dto.request.EventoRequest;
import terrabuio.heitor.abysslogv2.internal.evento.services.EventoService;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;
import terrabuio.heitor.abysslogv2.internal.monstro.domain.Monstro;
import terrabuio.heitor.abysslogv2.internal.monstro.services.MonstroService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AtribuirEvento {
    private final EventoService eventoService;
    private final ExpedicaoService expedicaoService;
    private final MonstroService monstroService;

    public void executar(EventoRequest request){
        Expedicao ex = expedicaoService.buscarPorId(request.idExpedicao());
        validarEstadoExpedicao(ex);


        Evento ev = new Evento();
        ev.setExpedicao(ex);
        ev.setDescricao(request.descricao());
        ev.setTipo(request.tipo());

        ev.setMonstros(buscarListaMonstros(request));
        ev.setData(request.data() != null ? request.data() : LocalDateTime.now());
        eventoService.salvar(ev);
    }

    private List<Monstro> buscarListaMonstros(EventoRequest request){
        List<Long> monstrosIDs = request.monstroId();

        if (monstrosIDs == null || monstrosIDs.isEmpty()) {
            return List.of();
        }
        return monstroService.buscarPorIds(monstrosIDs);
    }
    private void validarEstadoExpedicao(Expedicao ex) {
        if (ex.getStatus() == Expedicao.StatusExpedicao.PLANEJADA ||
                ex.getStatus() == Expedicao.StatusExpedicao.FINALIZADA) {
            throw new RuntimeException("Não é possível registrar eventos em expedições " + ex.getStatus());
        }
    }
}
