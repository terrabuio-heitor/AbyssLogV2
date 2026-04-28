package terrabuio.heitor.abysslogv2.internal.expedicao.usecases.finalizacao;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.internal.evento.domain.Evento;
import terrabuio.heitor.abysslogv2.internal.evento.services.EventoService;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;
import terrabuio.heitor.abysslogv2.internal.navio.services.NavioService;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class InterromperExpedicao {
    private final ExpedicaoService expedicaoService;
    private final FinalizarExpedicao finalizarExpedicao;
    private final EventoService eventoService;
    private final NavioService navioService;

    @Transactional
    public void executar(Long Id, Long eventoId){
        Expedicao ex = expedicaoService.buscarPorId(Id);
        finalizarExpedicao.validacao(ex);
        Evento ev = eventoService.buscarPorId(eventoId);
        switch (ev.getTipo()) {
            case "Naúfragio":
                navioService.buscarPorId(ex.getNavio().getId()).setStatus("Nas profundezas do Mar 💀");
                ex.setStatus(Expedicao.StatusExpedicao.INTERROMPIDA);
                ex.setDataFim(LocalDate.now());
                break;
            default:
                //--Ainda não tem.
                //--Vou colocar mais conforme for surgindo a necessidade;
        }

    }
}
