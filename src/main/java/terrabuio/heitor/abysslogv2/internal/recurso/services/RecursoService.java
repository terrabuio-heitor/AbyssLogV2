package terrabuio.heitor.abysslogv2.internal.recurso.services;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.internal.recurso.domain.Recurso;
import terrabuio.heitor.abysslogv2.internal.recurso.repository.RecursoRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecursoService {
    private final RecursoRepo recursoRepo;

    public List<Recurso> listarTodos() {
        return recursoRepo.findAll();
    }
    public Recurso buscarPorId(Long id) {
        return recursoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso não encontrado com o ID nessa expedição: " + id));
    }

    @Transactional
    public Recurso salvar(Recurso recurso) {
        // Aqui você poderia colocar validações extras
        return recursoRepo.save(recurso);
    }

    @Transactional
    public void excluir(Long id) {
        Recurso recurso = buscarPorId(id);
        recursoRepo.delete(recurso);
    }

    @Transactional
    public Recurso atualizar(Long id, Recurso dadosAtualizados) {
        Recurso recursoExistente = buscarPorId(id);
        recursoExistente.setNome(dadosAtualizados.getNome());
        recursoExistente.setQuantidade(dadosAtualizados.getQuantidade());
        recursoExistente.setExpedicao(dadosAtualizados.getExpedicao());
        return recursoRepo.save(recursoExistente);
    }

}
