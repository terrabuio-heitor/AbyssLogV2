package terrabuio.heitor.abysslogv2.internal.tripulante.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TripulanteRequest(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "O cargo é obrigatório")
        String cargo
) {}
