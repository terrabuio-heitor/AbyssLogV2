package terrabuio.heitor.abysslogv2.internal.navio.services;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.internal.navio.domain.Navio;
import terrabuio.heitor.abysslogv2.internal.navio.domain.TipoNavio;
import terrabuio.heitor.abysslogv2.internal.navio.dto.request.NavioRequest;
import terrabuio.heitor.abysslogv2.internal.navio.repository.NavioRepo;
import terrabuio.heitor.abysslogv2.internal.navio.repository.TipoNavioRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NavioService {
    private final NavioRepo navioRepo;
    private final TipoNavioRepo tipoNavioRepo;


    public List<Navio> listarTodos(){
        return navioRepo.findAll();
    }

    public List<TipoNavio> listarTodas(){ return tipoNavioRepo.findAll();}

    public Navio buscarPorId(Long id) {
        return navioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Navio não encontrado com o ID: " + id));
    }

    public TipoNavio buscaTipoPorID(Long id) {
        return tipoNavioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de Navio não encontrado com o ID: " + id));
    }
    @Transactional
    public Navio salvar(Navio navio) {
        return navioRepo.save(navio);
    }

    @Transactional
    public Navio salvarRequest(NavioRequest request) {
        TipoNavio tipoNavio = tipoNavioRepo.findById(request.tipoNavioId())
                .orElseThrow(() -> new RuntimeException("Projeto de Tipo de Navio não encontrado."));

        Navio novoNavio = terrabuio.heitor.abysslogv2.internal.navio.mapper.NavioMapper.toEntity(request, tipoNavio);
        return navioRepo.save(novoNavio);
    }

    @Transactional
    public void deletar(Long id) {
        Navio navio = buscarPorId(id);
        navioRepo.delete(navio);
    }

    @Transactional
    public Navio atualizar(Long id, Navio dadosAtualizados) {
        Navio navioExistente = buscarPorId(id);

        navioExistente.setNome(dadosAtualizados.getNome());
        navioExistente.setTipo(dadosAtualizados.getTipo());
        navioExistente.setCapacidadeTripulacao(dadosAtualizados.getCapacidadeTripulacao());
        navioExistente.setCapacidadeCarga(dadosAtualizados.getCapacidadeCarga());
        navioExistente.setVelocidade(dadosAtualizados.getVelocidade());
        navioExistente.setResistenciaMaxima(dadosAtualizados.getResistenciaMaxima());
        navioExistente.setStatus(dadosAtualizados.getStatus());

        return navioRepo.save(navioExistente);
    }

}
