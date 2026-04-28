package terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;
import terrabuio.heitor.abysslogv2.internal.tripulante.domain.Tripulante;
import terrabuio.heitor.abysslogv2.internal.tripulante.services.TripulanteService;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.domain.TripulanteExpedicao;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.dto.request.TripulanteExpedicaoRequest;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.dto.response.TripulanteExpedicaoResponse;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.mapper.TripulanteExpedicaoMapper;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.repository.TripulanteExpedicaoRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripulanteExpedicaoService {
    private final TripulanteExpedicaoRepo triExRepository;
    //N:N
    private final TripulanteService tripulanteService;
    private final ExpedicaoService expedicaoService;

    @Transactional
    public TripulanteExpedicaoResponse vincular(TripulanteExpedicaoRequest request) {
        Tripulante tripulante = tripulanteService.buscarPorId(request.idTripulante());
        Expedicao expedicao = expedicaoService.buscarPorId(request.idExpedicao());
        TripulanteExpedicao triEx = TripulanteExpedicaoMapper.toEntity(request, tripulante, expedicao);
        TripulanteExpedicao salvo = triExRepository.save(triEx);
        return TripulanteExpedicaoMapper.toResponse(salvo);
    }

    @Transactional
    public TripulanteExpedicao desvincular(Long id) {
        TripulanteExpedicao vinculo = triExRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vínculo não encontrado"));
        vinculo.setAtivo(false);
        vinculo.setDataSaida(LocalDate.now());
        return triExRepository.save(vinculo);
    }

    public List<TripulanteExpedicao> listar(){
        return triExRepository.findAll();
    }

    public List<TripulanteExpedicao> listarExpedicao(Long idExpedicao)
    {
        return triExRepository.findByExpedicaoIdAndAtivoTrue(idExpedicao);
    }


    @Transactional
    public void remover(Long id) {
        // Verificamos se existe antes de tentar deletar para não estourar erro feio de SQL
        if (!triExRepository.existsById(id)) {
            throw new RuntimeException("Vínculo não encontrado para remoção física.");
        }
        triExRepository.deleteById(id);
    }

    public Optional<TripulanteExpedicao> buscarPorTripulanteNaExpedicao(Long TripulanteId, Long ExpedicaoId) {
        return triExRepository.findByTripulanteIdAndExpedicaoIdAndAtivoTrue(TripulanteId, ExpedicaoId);
    }
}
