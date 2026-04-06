package terrabuio.heitor.abysslogv2.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.domain.Monstro;
import terrabuio.heitor.abysslogv2.domain.Navio;
import terrabuio.heitor.abysslogv2.repository.MonstroRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonstroService {
    private final MonstroRepo monstroRepo;

    public List<Monstro> listarTodos(){
        return monstroRepo.findAll();
    }

    public Monstro buscarPorId(Long id){
        return monstroRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expedição não encontrada!"));
    }

    public Monstro criar(Monstro monstro){
        return monstroRepo.save(monstro);
    }

    @Transactional
    public void deletar(Long id) {
        Monstro monstro = buscarPorId(id);
        monstroRepo.delete(monstro);
    }

    @Transactional
    public Monstro atualizar(Long id, Monstro dadosAtualizados) {
        Monstro monstroExistente = buscarPorId(id);

        monstroExistente.setNome(dadosAtualizados.getNome());
        monstroExistente.setDescricao(dadosAtualizados.getNome());
        monstroExistente.setNivelPerigo(dadosAtualizados.getNivelPerigo());

        return monstroRepo.save(monstroExistente);
    }
}
