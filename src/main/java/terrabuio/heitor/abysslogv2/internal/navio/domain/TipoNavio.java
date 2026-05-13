package terrabuio.heitor.abysslogv2.internal.navio.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.lang.Contract;

@Data
@Entity
@Table(name = "tiposNavio")
public class TipoNavio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;

    private Integer resistenciaBase;
    private Integer velocidadeBase;

    private float bonusTempestade;
    private float bonusCombate;

}