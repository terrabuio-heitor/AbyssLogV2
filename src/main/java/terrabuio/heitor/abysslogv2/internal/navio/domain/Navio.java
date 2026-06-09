package terrabuio.heitor.abysslogv2.internal.navio.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;


@Getter
@Setter
@Entity
@Table(name = "navios")
public class Navio{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String tipo;//a remoção é questão de tempo. ainda vou implementar o tipoNavio antes de mexer aqui, para não quebrar o código funcional atual

    private Integer capacidadeTripulacao; // camelCase aqui, o JPA traduz para snake_case

    private Integer capacidadeCarga;

    private Integer velocidade;

    //Sistema de simulação e resistencias.
    private Integer resistenciaMaxima;
    private Integer resistenciaAtual;

    @Range(min = 0, max = 55)
    private float defesa;

    @Range(min = 1500, max = 2100)
    private Integer anoFabricacao;

    @ManyToOne(fetch = FetchType.LAZY)
    TipoNavio tipoNavio;

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
    @Enumerated(EnumType.STRING)
    @Column(name = "qualidade")
    private QualidadeNavio qualidade;

    @RequiredArgsConstructor
    public enum QualidadeNavio{
        Comum("Comum", 1f),
        Raro("Raro", 1.22f),
        Lendario("Um Majestoso Lendário Navio", 1.68f) ;
        private final String descricao;
        @Getter
        private final float multiplicador;
        @JsonValue
        public String getDescricao() {
            return descricao;
        }
    }

    public static Navio criarNovoNavio(String nome, TipoNavio tipo, QualidadeNavio qualidade, Integer anoFabricacao) {
        Navio navio = new Navio();
        navio.setNome(nome);
        navio.setTipoNavio(tipo);
        navio.setTipo(tipo.getNome());//--Só para não apagar completamente o tipo
        navio.setQualidade(qualidade);
        navio.setAnoFabricacao(anoFabricacao);
        navio.setStatus(StatusNavio.DISPONIVEL);
        navio.calcularAtributosIniciais();
        return navio;
    }

    //O this = this aqui é uma bagunça de entender mesmo. To usando isso para recalcular os atributos quando for criado um navio.
    public void calcularAtributosIniciais(){
        if (this.tipoNavio == null || this.qualidade == null){
            throw new IllegalArgumentException("Não é possível calcular atributos de um navio sem tipo e nem qualidade");
        }
        float mult = this.qualidade.getMultiplicador();
        TipoNavio tipo = this.tipoNavio;

        this.velocidade = Math.round(tipo.getVelocidadeBase() * mult);
        this.resistenciaMaxima = Math.round(tipo.getResistenciaBase() * mult);

        this.resistenciaAtual = resistenciaMaxima;
        //Capacidades
        this.capacidadeCarga = Math.round(tipo.getCapacidadeCargaBase() * mult);
        this.capacidadeTripulacao = Math.round(tipo.getCapacidadeTripulantesBase() * mult);
    }
    public void receberDano(Integer danoBruto){
        //Validações e proteções.
        if (danoBruto == null || danoBruto <= 0) {
            return;
        }
        if (this.resistenciaMaxima == null || this.resistenciaMaxima <= 0) {
            throw new IllegalStateException("Resistência máxima inválida");
        }
        if (this.resistenciaAtual <= 0) return;
        //Vida do Navio em porcentagem
        float proporcaoVida = (float) this.resistenciaAtual / this.resistenciaMaxima;
        float defesaDegradada = this.defesa * proporcaoVida;

        float multiplicadorDano = 1-(defesaDegradada/100);

        int danoFinal = Math.round(danoBruto * multiplicadorDano);

        this.resistenciaAtual -= danoFinal;
        if(resistenciaAtual <= 0){
            this.status = StatusNavio.NAUFRAGADO;
        }
    }
}