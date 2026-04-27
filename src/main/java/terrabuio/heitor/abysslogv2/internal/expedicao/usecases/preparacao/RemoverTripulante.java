package terrabuio.heitor.abysslogv2.internal.expedicao.usecases.preparacao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.domain.TripulanteExpedicao;

import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.services.TripulanteExpedicaoService;

@Service
@RequiredArgsConstructor
public class RemoverTripulante {
    private final TripulanteExpedicaoService triExService;

    public void executar(Long idTripulante, Long idExpedicao){
        TripulanteExpedicao vinculo = triExService.buscarPorTripulanteNaExpedicao(idTripulante, idExpedicao)
                .orElseThrow(() -> new RuntimeException("Não foi possível localizar este tripulante ativo nesta expedição."));

        if(vinculo.getExpedicao().getStatus() != Expedicao.StatusExpedicao.PREPARANDO){
            throw new RuntimeException("Não é possível apagar um registro de um membro que participou da viagem, tente 'DESVINCULAR' ");
        }
        triExService.remover(vinculo.getId());
    }
}
