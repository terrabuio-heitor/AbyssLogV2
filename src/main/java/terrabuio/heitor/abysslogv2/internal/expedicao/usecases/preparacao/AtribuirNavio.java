package terrabuio.heitor.abysslogv2.internal.expedicao.usecases.preparacao;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;
import terrabuio.heitor.abysslogv2.internal.navio.domain.Navio;
import terrabuio.heitor.abysslogv2.internal.navio.services.NavioService;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.repository.TripulanteExpedicaoRepo;

@Service
@RequiredArgsConstructor
public class AtribuirNavio {
    private final ExpedicaoService expedicaoService;
    private final NavioService navioService;

    private final TripulanteExpedicaoRepo teRepo;
    @Transactional
    public void executar(Long expedicaoId, Long navioId){
        System.out.println("DEBUG: Tentando atribuir Navio ID " + navioId + " na Expedição ID " + expedicaoId);
        Expedicao ex = expedicaoService.buscarPorId(expedicaoId);
        Navio navio = navioService.buscarPorId(navioId);
        long tripulantesAtuais = teRepo.countByExpedicaoIdAndAtivoTrue(expedicaoId);

        if (ex == null) {
            throw new RuntimeException("Expedição não encontrada");
        }

        if (ex.getStatus() != Expedicao.StatusExpedicao.PLANEJADA &&
                ex.getStatus() != Expedicao.StatusExpedicao.PREPARANDO) {
            throw new RuntimeException("Não é possível alterar o navio de uma expedição em " + ex.getStatus().getDescricao());
        }

        //Vejo se o navio tá ok
        if(navio.getStatus() != Navio.StatusNavio.DISPONIVEL){
            throw new RuntimeException("Navio " + navio.getNome() + " não está disponível (Status: " + navio.getStatus().getDescricao() + ")");        }

        if (tripulantesAtuais > navio.getCapacidadeTripulacao()) {
            throw new RuntimeException("Novo navio não suporta a tripulação atual");
        }

        if (ex.getNavio() != null && !ex.getNavio().equals(navio)) {
            Navio navioAntigo = ex.getNavio();
            navioAntigo.setStatus(Navio.StatusNavio.DISPONIVEL);
            navioService.salvar(navioAntigo);
        }

        ex.setNavio(navio);
        ex.setStatus(Expedicao.StatusExpedicao.PREPARANDO);
        navio.setStatus(Navio.StatusNavio.ATIVO);


        navioService.salvar(navio);
        expedicaoService.atualizar(expedicaoId,ex);
    }
}
