package terrabuio.heitor.abysslogv2.internal.expedicao.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Registered;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.JdbcType;
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

    //@NotNull(message = "A data de início é obrigatória")
    private LocalDate dataInicio;

    private LocalDate dataFim;

    //private String status;
    @Enumerated(EnumType.STRING)
    private StatusExpedicao status ;

    @OneToMany(mappedBy = "expedicao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evento> eventos;

    //STATUS Expedicão
    @Getter
    @RequiredArgsConstructor
    public enum StatusExpedicao {
        PLANEJADA("Planejada"),
        PREPARANDO("Preparando"),
        PARADA("Parada"),
        ANDAMENTO("No Mar"),
        FINALIZADA("Finalizada"),
        INTERROMPIDA("Interrompida");
        private final String descricao;
    }
}

