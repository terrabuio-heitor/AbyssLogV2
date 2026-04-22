package terrabuio.heitor.abysslogv2.internal.expedicao.usecases.planejamento;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;

@Service
@RequiredArgsConstructor
public class AlterarExpedicao {

    private final ExpedicaoService service;
    @Transactional
    public Expedicao alterar(Long id, Expedicao dadosAtualizados){

        Expedicao expedicaoAtual = service.buscarPorId(id);

        if(expedicaoAtual.getStatus() != Expedicao.StatusExpedicao.PLANEJADA){
            throw new RuntimeException("Expedição já saiu do Planejamento, não dá para alterar");
        }

        aplicarAlteracoes(expedicaoAtual, dadosAtualizados);

        return service.atualizar(id, expedicaoAtual);
    }

    private void aplicarAlteracoes(Expedicao atual, Expedicao novosDados){
        // Exemplo de campos
        if(novosDados.getNome() != null){
            atual.setNome(novosDados.getNome());
        }

        if(novosDados.getNavio() != null){
            atual.setNavio(novosDados.getNavio());
        }
    }
}