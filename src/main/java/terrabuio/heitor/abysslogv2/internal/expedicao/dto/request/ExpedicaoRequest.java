package terrabuio.heitor.abysslogv2.internal.expedicao.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;

import java.time.LocalDate;

public record ExpedicaoRequest(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotNull(message = "O ID do navio é obrigatório")
        Long idNavio,
        @NotBlank(message = "O capitão é obrigatório")
        String capitao,
        @NotNull(message = "A data de início é obrigatória")
        LocalDate dataInicio
        //Expedicao.StatusExpedicao status
        //String status
) {}
