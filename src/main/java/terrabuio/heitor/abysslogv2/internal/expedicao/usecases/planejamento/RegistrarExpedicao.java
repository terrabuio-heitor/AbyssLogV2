package terrabuio.heitor.abysslogv2.internal.expedicao.usecases.planejamento;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.dto.request.ExpedicaoRequest;
import terrabuio.heitor.abysslogv2.internal.expedicao.mapper.ExpedicaoMapper;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;
import terrabuio.heitor.abysslogv2.internal.navio.domain.Navio;
import terrabuio.heitor.abysslogv2.internal.navio.services.NavioService;

@Service
@RequiredArgsConstructor
public class RegistrarExpedicao {
    private final ExpedicaoService expedicaoService;
    private final NavioService navioService;
    @Transactional
    public Expedicao iniciar(ExpedicaoRequest request) {
        Navio navio = null;
        if (request.idNavio() != null) {
            navio = navioService.buscarPorId(request.idNavio());
            if (!navio.getStatus().equals("DISPONIVEL")) {
                throw new RuntimeException("Navio " + navio.getNome() + " não está disponível no momento");
            }
        }
        Expedicao expedicao = ExpedicaoMapper.toEntity(request, navio);
        if (navio != null) {
            expedicao.setStatus(Expedicao.StatusExpedicao.PREPARANDO);
            navio.setStatus("ATIVO");
        } else {
            expedicao.setStatus(Expedicao.StatusExpedicao.PLANEJADA);
        }
        Expedicao validada = expedicao;
        return expedicaoService.iniciar(validada);
    }
}
