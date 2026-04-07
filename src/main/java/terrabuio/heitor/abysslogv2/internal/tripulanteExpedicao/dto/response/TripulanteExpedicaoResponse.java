package terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.dto.response;

public record TripulanteExpedicaoResponse(
        Long id,
        Long idTripulante,
        String nomeTripulante,
        String cargoTripulante,
        Long idExpedicao,
        String nomeExpedicao,
        java.time.LocalDate dataEntrada,
        java.time.LocalDate dataSaida,
        Boolean ativo
) {}
