package terrabuio.heitor.abysslogv2.internal.expedicao.usecases.execucao;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;

@Service
@RequiredArgsConstructor
public class PausarExpedicao{
    private final ExpedicaoService expedicaoService;
    @Transactional
    public void executar(Long Id){
        Expedicao ex = expedicaoService.buscarPorId(Id);
        if(ex.getStatus() != Expedicao.StatusExpedicao.ANDAMENTO){
            throw new RuntimeException("Como se pode parar uma expedição que não está no mar???");
        }
        ex.setStatus(Expedicao.StatusExpedicao.PARADA);
    }
}