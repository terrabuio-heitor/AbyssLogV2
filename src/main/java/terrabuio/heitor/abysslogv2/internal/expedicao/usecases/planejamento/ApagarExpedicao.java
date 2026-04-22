package terrabuio.heitor.abysslogv2.internal.expedicao.usecases.planejamento;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;

@Service
@RequiredArgsConstructor
public class ApagarExpedicao {
    private final ExpedicaoService expedicaoService;
    @Transactional
    public void executar(Long expedicaoId){
        Expedicao ex = expedicaoService.buscarPorId(expedicaoId);
        if(ex == null){
            throw new IllegalArgumentException();
        }
        if(ex.getStatus() !=  Expedicao.StatusExpedicao.PREPARANDO && ex.getStatus() !=  Expedicao.StatusExpedicao.PLANEJADA){
            throw new RuntimeException("Expedição já saio do papel");
        }
        expedicaoService.deletar(expedicaoId);




        expedicaoService.deletar(expedicaoId);
    }
}
