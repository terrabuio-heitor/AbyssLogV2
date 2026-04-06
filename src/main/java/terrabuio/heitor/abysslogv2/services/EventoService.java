package terrabuio.heitor.abysslogv2.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import terrabuio.heitor.abysslogv2.domain.Evento;
import terrabuio.heitor.abysslogv2.domain.Monstro;
import terrabuio.heitor.abysslogv2.repository.EventoRepo;
import terrabuio.heitor.abysslogv2.repository.MonstroRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {
    private final EventoRepo eventoRepo;
    private final MonstroRepo monstroRepository;

    public List<Evento> buscarTodos() {
        return eventoRepo.findAll();
    }

    public Evento buscarPorId(Long id){
        return eventoRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Evento não encontrado!"));
    }

    public Evento salvar(Evento evento){
        return eventoRepo.save(evento);
    }

    public void remover(Long id){
        eventoRepo.deleteById(id);
    }

    public Evento atualizar(Long id, Evento dadosAtualizados){
        Evento eventoExistente = buscarPorId(id);
        eventoExistente.setTipo(dadosAtualizados.getTipo());
        eventoExistente.setData(dadosAtualizados.getData());
        eventoExistente.setDescricao(dadosAtualizados.getDescricao());

        if (dadosAtualizados.getExpedicao() != null) {
            eventoExistente.setExpedicao(dadosAtualizados.getExpedicao());
        }
        return eventoRepo.save(eventoExistente);
    }

    @Transactional
    public Evento adicionarMonstrosAoEvento(Long eventoId, List<Long> monstrosIds) {
        Evento evento = eventoRepo.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        List<Monstro> monstros = monstroRepository.findAllById(monstrosIds);

        // Adiciona os novos monstros aos que já existem no evento
        evento.getMonstros().addAll(monstros);

        return eventoRepo.save(evento);
    }
}
