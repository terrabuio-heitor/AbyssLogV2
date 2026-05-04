package terrabuio.heitor.abysslogv2.internal.expedicao.usecases.execucao;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.repository.TripulanteExpedicaoRepo;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class IniciarExpedicao {
    private final ExpedicaoService expedicaoService;
    private final TripulanteExpedicaoRepo teRepo;

    @Transactional
    public void executar(Long expedicaoId){
        Expedicao expedicao = expedicaoService.buscarPorId(expedicaoId);
        long tripulantes = teRepo.countByExpedicaoIdAndAtivoTrue(expedicaoId);
        if(expedicao == null){
            throw new RuntimeException("Erro na expedicão");
        }
        if(expedicao.getStatus() != Expedicao.StatusExpedicao.PREPARANDO && Expedicao.StatusExpedicao.PARADA != expedicao.getStatus()){
            throw new RuntimeException("Expedição não pode ser iniciada");
        }
        if(tripulantes > 1){
            throw new RuntimeException("Navio com fantasmas como tripulantes não zarpa do porto amigo");
        }
        if (expedicao.getStatus() == Expedicao.StatusExpedicao.PREPARANDO) {
            expedicao.setDataInicio(LocalDate.now());
        }
        expedicao.setStatus(Expedicao.StatusExpedicao.ANDAMENTO);
    }
}
