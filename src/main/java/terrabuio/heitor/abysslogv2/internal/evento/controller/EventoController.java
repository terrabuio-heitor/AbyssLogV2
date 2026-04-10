package terrabuio.heitor.abysslogv2.internal.evento.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import terrabuio.heitor.abysslogv2.internal.evento.dto.request.EventoRequest;
import terrabuio.heitor.abysslogv2.internal.evento.dto.response.EventoResponse;
import terrabuio.heitor.abysslogv2.internal.evento.mapper.EventoMapper;
import terrabuio.heitor.abysslogv2.internal.evento.services.EventoService;
import terrabuio.heitor.abysslogv2.internal.evento.domain.Evento;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;
import terrabuio.heitor.abysslogv2.internal.monstro.domain.Monstro;
import terrabuio.heitor.abysslogv2.internal.monstro.services.MonstroService;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {
    private final EventoService eventoService;
    private final ExpedicaoService expedicaoService;
    private final MonstroService monstroService;

    //CRUD FUNCIONAL E Básico
    @GetMapping
    public List<EventoResponse> listar(){
        return eventoService.buscarTodos()
                .stream()
                .map(EventoMapper::toResponse)
                .toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> atualizar(@PathVariable Long id, @RequestBody @Valid Evento evento) {
        return ResponseEntity.ok(eventoService.atualizar(id, evento));
    }
    @PostMapping
    public EventoResponse iniciar(@RequestBody @Valid EventoRequest request){
        Expedicao expedicao = expedicaoService.buscarPorId(request.idExpedicao());

        List<Monstro> monstros = request.monstroId() != null
                ? monstroService.buscarPorIds(request.monstroId())
                : List.of();

        Evento evento = EventoMapper.toEntity(request, expedicao, monstros);

        Evento salva = eventoService.salvar(evento);

        return EventoMapper.toResponse(salva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        eventoService.remover(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}/adicionar-monstros")
    public ResponseEntity<Evento> adicionarMonstros(@PathVariable Long id, @RequestBody List<Long> monstrosIds) {
        return ResponseEntity.ok(eventoService.adicionarMonstrosAoEvento(id, monstrosIds));
    }
}