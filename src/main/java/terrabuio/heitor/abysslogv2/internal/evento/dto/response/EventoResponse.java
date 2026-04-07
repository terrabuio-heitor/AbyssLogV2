package terrabuio.heitor.abysslogv2.internal.evento.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record EventoResponse(
        Long id,
        String tipo,
        String descricao,
        LocalDateTime data,
        Long idExpedicao,
        List<String> nomesMonstros
) {}
