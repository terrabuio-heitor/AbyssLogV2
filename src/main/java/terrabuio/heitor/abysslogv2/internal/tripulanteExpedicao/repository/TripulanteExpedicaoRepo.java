package terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.domain.TripulanteExpedicao;

import java.util.List;

@Repository
public interface TripulanteExpedicaoRepo extends JpaRepository<TripulanteExpedicao,Long> {
    List<TripulanteExpedicaoRepo> findByExpedicaoIdAndAtivoTrue(Long expedicaoId);
}
