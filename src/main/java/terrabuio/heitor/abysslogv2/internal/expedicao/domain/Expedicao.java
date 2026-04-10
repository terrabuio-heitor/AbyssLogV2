package terrabuio.heitor.abysslogv2.internal.expedicao.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import terrabuio.heitor.abysslogv2.internal.navio.domain.Navio;
import terrabuio.heitor.abysslogv2.internal.evento.domain.Evento;

@Data
@Entity
@NoArgsConstructor
@Table(name = "expedicoes")

public class Expedicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "navio_id", nullable = false)
    @JsonManagedReference
    private Navio navio;

    @NotBlank(message = "O capitao é obrigatório")
    private String capitao;

    @Temporal(TemporalType.DATE)
    //@NotNull(message = "A data de início é obrigatória")
    private Date dataInicio;

    @Temporal(TemporalType.DATE)
    private Date dataFim;

    private String status;

    @OneToMany(mappedBy = "expedicao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evento> eventos;
}
