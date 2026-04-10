package terrabuio.heitor.abysslogv2.shared.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResponse(
        int status,
        String erro,
        List<String> mensagens,
        LocalDateTime timestamp
) {}