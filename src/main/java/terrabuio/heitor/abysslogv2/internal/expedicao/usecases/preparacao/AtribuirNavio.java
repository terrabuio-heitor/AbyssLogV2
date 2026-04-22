package terrabuio.heitor.abysslogv2.internal.expedicao.usecases.preparacao;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;
import terrabuio.heitor.abysslogv2.internal.navio.domain.Navio;
import terrabuio.heitor.abysslogv2.internal.navio.services.NavioService;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.repository.TripulanteExpedicaoRepo;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AtribuirNavio {
    private final ExpedicaoService expedicaoService;
    private final NavioService navioService;

    private final TripulanteExpedicaoRepo teRepo;
    @Transactional
    public void executar(Long navioId, Long expedicaoId){
        Navio navio = navioService.buscarPorId(navioId);
        Expedicao expedicao = expedicaoService.buscarPorId(expedicaoId);
        long tripulantes = teRepo.countByExpedicaoIdAndAtivoTrue(expedicaoId);

        //Validação Basica
        if (navio == null) {
            throw new RuntimeException("Navio não encontrado");
        }

        if (expedicao == null) {
            throw new RuntimeException("Expedição não encontrada");
        }
        //Vejo se o navio tá ok
        if(!Objects.equals(navio.getStatus(), "Disponível")){
            throw new RuntimeException("Navio Indisponível");
        }

        if (tripulantes > navio.getCapacidadeTripulacao()) {
            throw new RuntimeException("Novo navio não suporta a tripulação atual");
        }

        expedicao.setNavio(navio);
        expedicao.setStatus(Expedicao.StatusExpedicao.PREPARANDO);
        navio.setStatus("Ativo");


        expedicaoService.atualizar(expedicaoId,expedicao);
        navioService.salvar(navio);
    }
}
