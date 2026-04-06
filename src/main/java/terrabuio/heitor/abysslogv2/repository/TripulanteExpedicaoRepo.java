package terrabuio.heitor.abysslogv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import terrabuio.heitor.abysslogv2.domain.Tripulante;

import java.util.List;

@Repository
public interface TripulanteExpedicao extends JpaRepository<Tripulante,Long> {
    List<TripulanteExpedicao> findByExpedicaoIdAndAtivoTrue(Long expedicaoId);
}
