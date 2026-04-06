package terrabuio.heitor.abysslogv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import terrabuio.heitor.abysslogv2.domain.TripulanteExpedicao;

import java.util.List;

@Repository
public interface TripulanteExpedicaoRepo extends JpaRepository<TripulanteExpedicao,Long> {
    List<TripulanteExpedicaoRepo> findByExpedicaoIdAndAtivoTrue(Long expedicaoId);
}
