package terrabuio.heitor.abysslogv2.internal.navio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import terrabuio.heitor.abysslogv2.internal.navio.domain.TipoNavio;

@Repository
public interface TipoNavioRepo extends JpaRepository <TipoNavio, Long>{
}
