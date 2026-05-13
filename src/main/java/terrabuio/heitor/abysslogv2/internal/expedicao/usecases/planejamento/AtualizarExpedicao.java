package terrabuio.heitor.abysslogv2.internal.expedicao.usecases.planejamento;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.dto.request.ExpedicaoRequest;
import terrabuio.heitor.abysslogv2.internal.expedicao.dto.response.ExpedicaoResponse;
import terrabuio.heitor.abysslogv2.internal.expedicao.mapper.ExpedicaoMapper;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AtualizarExpedicao {
    private final ExpedicaoService expedicaoService;

    @Transactional
    public ExpedicaoResponse executar(Long id, ExpedicaoRequest request) {
        Expedicao ex = expedicaoService.buscarPorId(id);
        if (request.nome() != null) {
            ex.setNome(request.nome());
        }
        if (request.dataInicio() != null) {
            validarData(ex, request.dataInicio());
            ex.setDataInicio(request.dataInicio());
        }
        if (request.capitao() != null) {
            ex.setCapitao(request.capitao());
        }
        return ExpedicaoMapper.toResponse(ex);
    }

    @Transactional
    private void validarData(Expedicao ex, @NotNull LocalDate data){
        if (ex.getDataFim() != null && ex.getDataFim().isBefore(data)) {
            throw new IllegalArgumentException("Data de início [" + data + "] não pode ser após a data de fim [" + ex.getDataFim() + "]");
        }
    }
}
