package terrabuio.heitor.abysslogv2.internal.navio.domain;

import jakarta.persistence.*;
import lombok.Data;

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

    private Integer resistencia;

    private Integer anoFabricacao;

    private String status;
}