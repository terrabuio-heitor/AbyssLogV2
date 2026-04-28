package terrabuio.heitor.abysslogv2.internal.expedicao.usecases.finalizacao;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;
import terrabuio.heitor.abysslogv2.internal.navio.domain.Navio;
import terrabuio.heitor.abysslogv2.internal.navio.services.NavioService;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FinalizarExpedicao {
    private final ExpedicaoService expedicaoService;
    private final DesembarcarTripulacao desembarcarTripulacao;
    private final NavioService navioService;

    @Transactional
    public void executar(Long IdExpedicao){
        Expedicao ex = expedicaoService.buscarPorId(IdExpedicao);
        validacao(ex);
        ex.getNavio().getId();
        Navio navio = navioService.buscarPorId(ex.getNavio().getId());
        navio.setStatus("Disponível");
        desembarcarTripulacao.executar(ex.getId());
        ex.setDataFim(LocalDate.now());
        ex.setStatus(Expedicao.StatusExpedicao.FINALIZADA);
    }

    public void validacao(Expedicao expedicao){
        if(expedicao.getStatus() == Expedicao.StatusExpedicao.FINALIZADA
                || expedicao.getStatus() == Expedicao.StatusExpedicao.PLANEJADA
                || expedicao.getStatus() == Expedicao.StatusExpedicao.PREPARANDO
                || expedicao.getStatus() == Expedicao.StatusExpedicao.INTERROMPIDA
        )
        {
            throw new RuntimeException("Expedição está em estado inválido para ser finalizada " + expedicao.getStatus());
        }
    }
}
