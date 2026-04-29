package terrabuio.heitor.abysslogv2.internal.expedicao.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import terrabuio.heitor.abysslogv2.internal.evento.dto.response.EventoResponse;
import terrabuio.heitor.abysslogv2.internal.monstro.dto.response.MonstroResponse;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public record DiarioBordoResponse (
        String nome,
        String nomeExpedicao,
        String nomeNavio,
        LocalDate inicio,
        LocalDate fim,
        List<EventoResponse> eventos,
        List<MonstroResponse> monstros,
        Period tempoDeViagem,
        statusViagem status
){
    @RequiredArgsConstructor
    public enum statusViagem{
        FINALIZADA("Finalizada com SUCESSO!!"),
        INTERROMPIDA("Viagem precisou ser interrompida e não pode ser concluída!!"),
        NAUFRAGADO("A viagem terminou em um destino trágico e acabou no fundo do mar");

        private final String descricao;

        @JsonValue
        public String getDescricao() {
            return descricao;
        }
    }
}
