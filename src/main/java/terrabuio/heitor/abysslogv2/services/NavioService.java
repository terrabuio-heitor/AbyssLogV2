package terrabuio.heitor.abysslogv2.services;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.domain.Navio;
import terrabuio.heitor.abysslogv2.repository.NavioRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NavioService {
    private final NavioRepo navioRepo;

    public List<Navio> listarTodos(){
        return navioRepo.findAll();
    }

    public Navio buscarPorId(Long id) {
        return navioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Navio não encontrado com o ID: " + id));
    }

    @Transactional
    public Navio salvar(Navio navio) {
        // Aqui você poderia colocar validações extras
        // Ex: navio.setStatus("ATIVO");
        return navioRepo.save(navio);
    }

    @Transactional
    public void deletar(Long id) {
        Navio navio = buscarPorId(id);
        navioRepo.delete(navio);
    }

    @Transactional
    public Navio atualizar(Long id, Navio dadosAtualizados) {
        Navio navioExistente = buscarPorId(id);

        navioExistente.setNome(dadosAtualizados.getNome());
        navioExistente.setTipo(dadosAtualizados.getTipo());
        navioExistente.setCapacidadeTripulacao(dadosAtualizados.getCapacidadeTripulacao());
        navioExistente.setCapacidadeCarga(dadosAtualizados.getCapacidadeCarga());
        navioExistente.setVelocidade(dadosAtualizados.getVelocidade());
        navioExistente.setResistencia(dadosAtualizados.getResistencia());
        navioExistente.setStatus(dadosAtualizados.getStatus());

        return navioRepo.save(navioExistente);
    }

}
