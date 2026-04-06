package terrabuio.heitor.abysslogv2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "eventos")
public class Evento{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;

    @NotBlank(message = "Deve haver tipo")
    private String descricao ;

    @NotBlank(message = "Deve haver descrição")
    private java.time.LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "expedicao_id", nullable = false)
    @JsonIgnore
    private Expedicao expedicao;
    //evento_monstro
    @ManyToMany
    @JoinTable(
            name = "evento_monstro",
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "monstro_id")
    )
    private List<Monstro> monstros;
}