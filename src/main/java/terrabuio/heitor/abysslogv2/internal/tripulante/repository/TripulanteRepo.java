package terrabuio.heitor.abysslogv2.internal.tripulante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import terrabuio.heitor.abysslogv2.internal.tripulante.domain.Tripulante;

@Repository
public interface TripulanteRepo extends JpaRepository<Tripulante,Long> {
}
