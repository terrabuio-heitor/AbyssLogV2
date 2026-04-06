package terrabuio.heitor.abysslogv2.domain;

import jakarta.persistence.*;
import lombok.Data;

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