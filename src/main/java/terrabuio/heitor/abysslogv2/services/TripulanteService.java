package terrabuio.heitor.abysslogv2.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.domain.Tripulante;
import terrabuio.heitor.abysslogv2.repository.TripulanteRepo;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TripulanteService {
    private final TripulanteRepo tripulanteRepo;

    public List<Tripulante> listarTodos(){
        return tripulanteRepo.findAll();
    }

    public Tripulante buscarPorId(Long id) {
        return tripulanteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Tripulante não encontrado com o ID: " + id));
    }

    @Transactional
    public Tripulante salvar(Tripulante tripulante) {
        // Aqui você poderia colocar validações extras
        return tripulanteRepo.save(tripulante);
    }

    @Transactional
    public void excluir(Long id) {
        Tripulante tripulante = buscarPorId(id);
        tripulanteRepo.delete(tripulante);
    }

    @Transactional
    public Tripulante atualizar(Long id, Tripulante dadosAtualizados) {
        Tripulante tripulanteExiste = buscarPorId(id);
        tripulanteExiste.setNome(dadosAtualizados.getNome());
        tripulanteExiste.setCargo(dadosAtualizados.getCargo());
        return tripulanteRepo.save(tripulanteExiste);
    }

}
