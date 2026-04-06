package terrabuio.heitor.abysslogv2.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.domain.Expedicao;
import terrabuio.heitor.abysslogv2.repository.RepoExpedicao;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceExpedicao {
    private final RepoExpedicao repoExpedicao;

    public List<Expedicao> listarTodos(){
        return repoExpedicao.findAll();
    }

    public Expedicao buscarPorId(Long id){
        return repoExpedicao.findById(id)
        .orElseThrow(() -> new RuntimeException("Expedição não encontrada!"));
    }

    public Expedicao iniciar(Expedicao expedicao){
        if(expedicao.getStatus() == null){
            expedicao.setStatus("Planejada");
        }
        return repoExpedicao.save(expedicao);
    }

    public Expedicao atualizar(Long id, Expedicao dadosAtualizados) {
        Expedicao expedicaoExistente = buscarPorId(id);

        expedicaoExistente.setNome(dadosAtualizados.getNome());
        expedicaoExistente.setCapitao(dadosAtualizados.getCapitao());
        expedicaoExistente.setStatus(dadosAtualizados.getStatus());
        expedicaoExistente.setDataInicio(dadosAtualizados.getDataInicio());

        // Se quiser permitir trocar o navio da expedição:
        if (dadosAtualizados.getNavio() != null) {
            expedicaoExistente.setNavio(dadosAtualizados.getNavio());
        }

        return repoExpedicao.save(expedicaoExistente);
    }

    @Transactional
    public void deletar(Long id) {
        // Lembre-se: No seu SQL você colocou ON DELETE CASCADE para eventos e recursos.
        // O banco vai apagar tudo que estiver ligado a essa expedição automaticamente.
        if (!repoExpedicao.existsById(id)) {
            throw new RuntimeException("Expedição não encontrada.");
        }
        repoExpedicao.deleteById(id);
    }
}
