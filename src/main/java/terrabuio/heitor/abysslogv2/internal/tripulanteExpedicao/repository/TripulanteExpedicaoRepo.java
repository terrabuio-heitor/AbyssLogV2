package terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.domain.TripulanteExpedicao;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripulanteExpedicaoRepo extends JpaRepository<TripulanteExpedicao,Long> {
    List<TripulanteExpedicaoRepo> findByExpedicaoIdAndAtivoTrue(Long expedicaoId);
    long countByExpedicaoIdAndAtivoTrue(Long expedicaoId);
    Optional<TripulanteExpedicao> findByTripulanteIdAndExpedicaoIdAndAtivoTrue(Long tripulanteId, Long expedicaoId);
}
