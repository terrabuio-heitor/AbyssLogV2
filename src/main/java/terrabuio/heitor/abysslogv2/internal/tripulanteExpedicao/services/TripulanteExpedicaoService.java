package terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.domain.TripulanteExpedicao;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.repository.TripulanteExpedicaoRepo;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TripulanteExpedicaoService {
    private final TripulanteExpedicaoRepo repository;

    @Transactional
    public TripulanteExpedicao vincular(TripulanteExpedicao vinculo) {
        vinculo.setDataEntrada(LocalDate.now());
        vinculo.setAtivo(true);
        return repository.save(vinculo);
    }
    @Transactional
    public TripulanteExpedicao desvincular(Long id) {
        TripulanteExpedicao vinculo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vínculo não encontrado"));
        vinculo.setAtivo(false);
        vinculo.setDataSaida(LocalDate.now());
        return repository.save(vinculo);
    }
}
