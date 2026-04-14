package terrabuio.heitor.abysslogv2.internal.expedicao.usecases.planejamento;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;

@Service
@RequiredArgsConstructor
public class RegistrarExpedicao {
    private final ExpedicaoService expedicaoService;

    public Expedicao iniciar(Expedicao expedicao){
        if (expedicao.getNavio() != null) {
            expedicao.setStatus(Expedicao.StatusExpedicao.PREPARANDO);
        } else {
            expedicao.setStatus(Expedicao.StatusExpedicao.PLANEJADA);
        }
        return expedicaoService.iniciar(expedicao);
    }
}
