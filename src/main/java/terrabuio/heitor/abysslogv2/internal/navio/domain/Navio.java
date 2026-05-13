package terrabuio.heitor.abysslogv2.internal.navio.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "navios")
public class Navio{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String tipo;

    private Integer capacidadeTripulacao; // camelCase aqui, o JPA traduz para snake_case

    private Integer capacidadeCarga;

    private Integer velocidade;

    private Integer resistenciaMaxima;
    private Integer resistenciaAtual;
    private Integer defesa;


    private Integer anoFabricacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusNavio status;

    @RequiredArgsConstructor
    public enum StatusNavio{
        MANUTENCAO ("Em manutenção"),
        ATIVO ("Em Atividade"),
        DISPONIVEL ("Disponível"),
        NAUFRAGADO ("No fundo do Mar");
        private final String descricao;

        @JsonValue
        public String getDescricao() {
            return descricao;
        }
    }
    @ManyToOne(fetch = FetchType.LAZY)
    TipoNavio tipoNavio;
}