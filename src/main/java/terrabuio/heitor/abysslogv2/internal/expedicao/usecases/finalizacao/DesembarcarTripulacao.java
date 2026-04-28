package terrabuio.heitor.abysslogv2.internal.expedicao.usecases.finalizacao;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.domain.TripulanteExpedicao;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.services.TripulanteExpedicaoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DesembarcarTripulacao {
    private final ExpedicaoService expedicaoService;
    private final TripulanteExpedicaoService triExService;

    @Transactional
    public void executar(Long IdExpedicao) {
        Expedicao ex = expedicaoService.buscarPorId(IdExpedicao);
        List<TripulanteExpedicao> triExList = triExService.listarExpedicao(ex.getId());
        for  (TripulanteExpedicao triEx : triExList) {
            triExService.desvincular(triEx.getId());
        }

    }
}
