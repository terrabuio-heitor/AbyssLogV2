package terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.dto.request;

import jakarta.validation.constraints.NotNull;

public record TripulanteExpedicaoRequest(
        @NotNull(message = "O ID do tripulante é obrigatório")
        Long idTripulante,
        @NotNull(message = "O ID da expedição é obrigatório")
        Long idExpedicao,
        @NotNull(message = "A data de entrada é obrigatória")
        java.time.LocalDate dataEntrada
) {}
