package terrabuio.heitor.abysslogv2.internal.expedicao.usecases.finalizacao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.internal.evento.domain.Evento;
import terrabuio.heitor.abysslogv2.internal.evento.dto.response.EventoResponse;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.dto.response.DiarioBordoResponse;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;
import terrabuio.heitor.abysslogv2.internal.monstro.domain.Monstro;
import terrabuio.heitor.abysslogv2.internal.monstro.dto.response.MonstroResponse;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GerarDiarioDeBordo {
    private final ExpedicaoService expedicaoService;

    public DiarioBordoResponse executar(Long idExpedicao){
        Expedicao ex = expedicaoService.buscarPorId(idExpedicao);
        validar(ex);
        return new DiarioBordoResponse(
                "Diário de Bordo da expedicão: " + ex.getNome(),ex.getNome(),
                ex.getNavio().getNome(), ex.getDataInicio(),
                ex.getDataFim(), eventos(ex) ,
                monstrosNaExpedicao(ex), calcularTempoDeViagem(ex), status(ex)
        );
    }
    private void validar(Expedicao expedicao){
        if(expedicao.getStatus() != Expedicao.StatusExpedicao.FINALIZADA && Expedicao.StatusExpedicao.INTERROMPIDA != expedicao.getStatus()){
            throw new RuntimeException("O relatório só deve ser feito quando a viagem terminar.");
        }
    }
    private Period calcularTempoDeViagem(Expedicao expedicao){
        LocalDate inicio = expedicao.getDataInicio();
        LocalDate fim = expedicao.getDataFim();
        if (fim == null){
            //return Period.ZERO;
            throw new RuntimeException("Dados Inconstantes no banco de dados. ");

        }
        return Period.between(inicio, fim);
    }
    private List<EventoResponse> eventos(Expedicao expedicao){
        return expedicao.getEventos().stream().map
                (evento ->
                        new EventoResponse(evento.getId(),evento.getDescricao(),
                                evento.getTipo(),evento.getData(),
                                expedicao.getId(), evento.getMonstros() != null
                                ? evento.getMonstros().stream()
                                  .map(Monstro::getNome)
                                  .toList()
                                : List.of())
                ).toList();
    }
    private List<MonstroResponse> monstrosNaExpedicao(Expedicao expedicao){
        List<Evento> eventos = expedicao.getEventos();
        return eventos
                .stream()
                .flatMap(evento -> evento.getMonstros().stream())
                .distinct()
                .map(
                        monstro ->
                                new MonstroResponse(monstro.getId(), monstro.getNome(),
                                        monstro.getNivelPerigo(), monstro.getDescricao())
                )
                .toList();
    }
    private DiarioBordoResponse.statusViagem status(Expedicao expedicao){
        if (expedicao.getStatus() == Expedicao.StatusExpedicao.FINALIZADA){
            return DiarioBordoResponse.statusViagem.FINALIZADA;
        }
        if (expedicao.getStatus() == Expedicao.StatusExpedicao.INTERROMPIDA){
            boolean houveNaufragio = expedicao.getEventos().stream()
                    .anyMatch(e -> "Naufrágio".equals(e.getTipo()));
            if (houveNaufragio){
                return DiarioBordoResponse.statusViagem.NAUFRAGADO;
            }
            else
                return DiarioBordoResponse.statusViagem.INTERROMPIDA;

        }
        return null;
    }

}
