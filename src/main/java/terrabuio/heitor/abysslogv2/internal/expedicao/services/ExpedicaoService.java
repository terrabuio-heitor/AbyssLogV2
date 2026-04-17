package terrabuio.heitor.abysslogv2.internal.expedicao.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.repository.ExpedicaoRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpedicaoService {
    private final ExpedicaoRepo expedicaoRepo;

    public List<Expedicao> listarTodos(){
        return expedicaoRepo.findAll();
    }

    public Expedicao buscarPorId(Long id){
        return expedicaoRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Expedição não encontrada!"));
    }

    public Expedicao iniciar(Expedicao expedicao){
        expedicao.setStatus(Expedicao.StatusExpedicao.PLANEJADA);
        return expedicaoRepo.save(expedicao);
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

        return expedicaoRepo.save(expedicaoExistente);
    }

    @Transactional
    public void deletar(Long id) {
        // Lembre-se: No seu SQL você colocou ON DELETE CASCADE para eventos e recursos.
        // O banco vai apagar tudo que estiver ligado a essa expedição automaticamente.
        if (!expedicaoRepo.existsById(id)) {
            throw new RuntimeException("Expedição não encontrada.");
        }
        expedicaoRepo.deleteById(id);
    }
}
