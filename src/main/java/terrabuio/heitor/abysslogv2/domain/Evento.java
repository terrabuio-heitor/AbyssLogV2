package terrabuio.heitor.abysslogv2.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "eventos")
public class Evento{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipo;
    private String descricao ;
    private LocalDate data;
    @Column(name = "expedicao_id")
    private int expedicaoId;
    public Eventos(){}

    public void validaEvento(){
        if (descricao == null || descricao.trim().isEmpty()){
            throw new IllegalArgumentException("descrição do evento obrigatória");
        }

        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("tipo do evento obrigatório");
        }
    }
}