package terrabuio.heitor.abysslogv2.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "recursos")

public class Recurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "expedicao_id", nullable = false)
    private Expedicao expedicao;
    public void valida(){
        quantidade = quantidade < 0 ? 0 : quantidade;
    }
}
