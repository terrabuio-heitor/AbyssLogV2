package terrabuio.heitor.abysslogv2.internal.recurso.domain;

import jakarta.persistence.*;
import lombok.Data;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;

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
