package terrabuio.heitor.abysslogv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Recurso extends JpaRepository<Recurso,Long> {
}
