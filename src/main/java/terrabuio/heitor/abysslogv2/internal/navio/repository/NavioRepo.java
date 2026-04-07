package terrabuio.heitor.abysslogv2.internal.navio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import terrabuio.heitor.abysslogv2.internal.navio.domain.Navio;

@Repository
public interface NavioRepo extends JpaRepository<Navio, Long>{
    /* --CRUD */
}
