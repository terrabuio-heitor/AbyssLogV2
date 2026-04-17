package terrabuio.heitor.abysslogv2.internal.expedicao.usecases.preparacao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;
import terrabuio.heitor.abysslogv2.internal.tripulante.domain.Tripulante;
import terrabuio.heitor.abysslogv2.internal.tripulante.services.TripulanteService;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.domain.TripulanteExpedicao;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.repository.TripulanteExpedicaoRepo;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class AtribuirTripulante {
    private final ExpedicaoService expedicaoService;
    private final TripulanteService tripulanteService;
    //private final TripulanteExpedicaoService teService;
    private final TripulanteExpedicaoRepo repo;

    public void executar(Long tripulanteId, Long expedicaoId){

        Tripulante tripulante = tripulanteService.buscarPorId(tripulanteId);
        Expedicao expedicao = expedicaoService.buscarPorId(expedicaoId);

        if (tripulante == null) {
            throw new RuntimeException("Tripulante não encontrado");
        }

        if (expedicao == null) {
            throw new RuntimeException("Expedição não encontrada");
        }

        if (expedicao.getNavio() == null) {
            throw new RuntimeException("Expedição sem navio");
        }

//        if (tripulante.isAtivo()) {
//            throw new RuntimeException("Tripulante já está em uma expedição");
//        }

        if (expedicao.getStatus() != Expedicao.StatusExpedicao.PREPARANDO &&
                expedicao.getStatus() != Expedicao.StatusExpedicao.PARADA) {
            throw new RuntimeException("Expedição não permite adicionar tripulantes");
        }

        long quantidade = repo.countByExpedicaoIdAndAtivoTrue(expedicaoId);

        if (quantidade >= expedicao.getNavio().getCapacidadeTripulacao()) {
            throw new RuntimeException("Capacidade do navio atingida");
        }

        //tripulante.setAtivo(true);
        TripulanteExpedicao te = new TripulanteExpedicao();
        te.setTripulante(tripulante);
        te.setDataEntrada(LocalDate.now());
        te.setExpedicao(expedicao);
        te.setAtivo(true);
        repo.save(te);
        //teService.vincular(te)
    }
}
