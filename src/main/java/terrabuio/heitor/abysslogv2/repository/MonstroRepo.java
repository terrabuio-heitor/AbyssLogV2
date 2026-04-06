package terrabuio.heitor.abysslogv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import terrabuio.heitor.abysslogv2.domain.Monstro;

@Repository
public interface MonstroRepo extends JpaRepository<Monstro,Long> {
}
