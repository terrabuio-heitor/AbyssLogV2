package terrabuio.heitor.abysslogv2.internal.expedicao.usecases.finalizacao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.internal.evento.domain.Evento;
import terrabuio.heitor.abysslogv2.internal.evento.services.EventoService;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;
import terrabuio.heitor.abysslogv2.internal.monstro.domain.Monstro;
import terrabuio.heitor.abysslogv2.internal.monstro.services.MonstroService;
import terrabuio.heitor.abysslogv2.internal.tripulante.services.TripulanteService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GerarDiarioDeBordo {
    private final MonstroService monstroService;
    private ExpedicaoService expedicaoService;
    private TripulanteService tripulanteService;
    private EventoService eventoService;

    public void executar(Long idExpedicao){
        Expedicao ex = expedicaoService.buscarPorId(idExpedicao);

    }
    private void validar(Expedicao expedicao){
        if(expedicao.getStatus() != Expedicao.StatusExpedicao.FINALIZADA && Expedicao.StatusExpedicao.INTERROMPIDA == expedicao.getStatus()){
            throw new RuntimeException("O relatório só deve ser feito quando a viagem terminar.");
        }
    }
    private Period calcularTempoDeViagem(Expedicao expedicao){
        LocalDate inicio = expedicao.getDataInicio();
        LocalDate fim = expedicao.getDataFim();
        return Period.between(inicio, fim);
    }
    private List<Monstro> monstrosNaExpedicao(Expedicao expedicao){
        List<Evento> eventos = expedicao.getEventos();
        return eventos
                .stream()
                .flatMap(evento -> evento.getMonstros().stream())
                .distinct()
                .toList();
    }
    private String Status(Expedicao expedicao){
        if (expedicao.getStatus() == Expedicao.StatusExpedicao.FINALIZADA){
            return "Finalizado com sucesso!!";
        }
        if (expedicao.getStatus() == Expedicao.StatusExpedicao.INTERROMPIDA){
            return "Viagem com destino trágico 💀";
        }
        return null;
    }

}
