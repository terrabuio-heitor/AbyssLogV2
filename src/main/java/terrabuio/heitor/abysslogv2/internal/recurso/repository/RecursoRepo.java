package terrabuio.heitor.abysslogv2.internal.recurso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import terrabuio.heitor.abysslogv2.internal.recurso.domain.Recurso;

@Repository
public interface RecursoRepo extends JpaRepository<Recurso,Long> {
}
