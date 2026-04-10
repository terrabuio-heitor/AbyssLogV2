package terrabuio.heitor.abysslogv2.internal.monstro.mapper;

import terrabuio.heitor.abysslogv2.internal.monstro.domain.Monstro;
import terrabuio.heitor.abysslogv2.internal.monstro.dto.request.MonstroRequest;
import terrabuio.heitor.abysslogv2.internal.monstro.dto.response.MonstroResponse;

public class MonstroMapper {
    public static Monstro toEntity(MonstroRequest request) {
        Monstro monstro = new Monstro();

        monstro.setNome(request.nome());
        monstro.setNivelPerigo(request.nivelPerigo());
        monstro.setDescricao(request.descricao());
        return monstro;
    }
    public MonstroResponse toResponse(Monstro monstro){
        return new MonstroResponse(
                monstro.getId(),
                monstro.getNome(),
                monstro.getNivelPerigo(),
                monstro.getDescricao());
    }
}
