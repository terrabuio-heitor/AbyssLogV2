package terrabuio.heitor.abysslogv2.internal.navio.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

@Getter
@Setter
@Entity
@Table(name = "tiposNavio")
public class TipoNavio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome;

    private String descricao;

    public Integer capacidadeTripulantesBase;
    public Integer capacidadeCargaBase;

    private Integer resistenciaBase;
    private Integer velocidadeBase;

    private float bonusTempestade;
    private float bonusCombate;

}