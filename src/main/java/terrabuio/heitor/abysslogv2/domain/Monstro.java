package terrabuio.heitor.abysslogv2.domain;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "monstros")
public class Monstro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private int nivelPerigo;
    private String descricao;
}
