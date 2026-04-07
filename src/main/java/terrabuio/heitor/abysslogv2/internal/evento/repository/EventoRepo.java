package terrabuio.heitor.abysslogv2.internal.evento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import terrabuio.heitor.abysslogv2.internal.evento.domain.Evento;

@Repository
public interface EventoRepo extends JpaRepository<Evento, Long> {
}
