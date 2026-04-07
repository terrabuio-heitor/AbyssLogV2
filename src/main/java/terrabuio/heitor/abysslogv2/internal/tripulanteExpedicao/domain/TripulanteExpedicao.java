package terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.domain;

import jakarta.persistence.*;
import lombok.Data;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.tripulante.domain.Tripulante;

@Data
@Entity
@Table(name = "tripulante_expedicao", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tripulante_id", "expedicao_id"})
})
public class TripulanteExpedicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tripulante_id")
    private Tripulante tripulante;

    @ManyToOne
    @JoinColumn(name = "expedicao_id")
    private Expedicao expedicao;

    private java.time.LocalDate dataEntrada;

    private java.time.LocalDate dataSaida;

    private Boolean ativo = true;
}