package terrabuio.heitor.abysslogv2.internal.expedicao.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;

@Repository
public interface ExpedicaoRepo extends JpaRepository<Expedicao, Long> {
}
