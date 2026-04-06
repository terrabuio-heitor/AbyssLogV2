package terrabuio.heitor.abysslogv2.services;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.domain.Navio;
import terrabuio.heitor.abysslogv2.repository.RepoExpedicao;
import terrabuio.heitor.abysslogv2.repository.RepoNavio;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceNavio {
    private final RepoNavio repoNavio;

    public List<Navio> listarTodos(){
        return repoNavio.findAll();
    }

    public Navio buscarPorId(Long id) {
        return repoNavio.findById(id)
                .orElseThrow(() -> new RuntimeException("Navio não encontrado com o ID: " + id));
    }

    @Transactional
    public Navio salvar(Navio navio) {
        // Aqui você poderia colocar validações extras
        // Ex: navio.setStatus("ATIVO");
        return repoNavio.save(navio);
    }

    @Transactional
    public void deletar(Long id) {
        Navio navio = buscarPorId(id);
        repoNavio.delete(navio);
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

        return repoNavio.save(navioExistente);
    }

}
