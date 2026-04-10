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
    public TripulanteExpedicao apagar(Long id) {
        TripulanteExpedicao vinculo = triExRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vínculo não encontrado"));
        vinculo.setAtivo(false);
        vinculo.setDataSaida(LocalDate.now());
        return triExRepository.save(vinculo);
    }

    public List<TripulanteExpedicao> listar(){
        return triExRepository.findAll();
    }

    @Transactional
    public TripulanteExpedicao finalizar(Long id) {
        TripulanteExpedicao vinculo = triExRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vínculo não encontrado"));

        if (!vinculo.getAtivo()) {
            throw new RuntimeException("Vínculo já está finalizado");
        }

        vinculo.setAtivo(false);
        vinculo.setDataSaida(LocalDate.now());

        return triExRepository.save(vinculo);
    }
}
