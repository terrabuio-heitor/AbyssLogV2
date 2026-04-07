package terrabuio.heitor.abysslogv2.internal.monstro.dto.response;

public record MonstroResponse(
        Long id,
        String nome,
        String nivelPerigo,
        String descricao
) {}