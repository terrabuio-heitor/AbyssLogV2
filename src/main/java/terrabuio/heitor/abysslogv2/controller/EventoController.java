package terrabuio.heitor.abysslogv2.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import terrabuio.heitor.abysslogv2.domain.Evento;
import terrabuio.heitor.abysslogv2.services.EventoService;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {
    private final EventoService eventoService;

    @GetMapping
    public List<Evento> listar() {
        return eventoService.buscarTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> atualizar(@PathVariable Long id, @RequestBody @Valid Evento evento) {
        return ResponseEntity.ok(eventoService.atualizar(id, evento));
    }

    @PostMapping
    public ResponseEntity<Evento> iniciar(@RequestBody @Valid Evento evento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.salvar(evento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        eventoService.remover(id);
        return ResponseEntity.noContent().build();
    }
    ///
    @PatchMapping("/{id}/adicionar-monstros")
    public ResponseEntity<Evento> adicionarMonstros(@PathVariable Long id, @RequestBody List<Long> monstrosIds) {
        return ResponseEntity.ok(eventoService.adicionarMonstrosAoEvento(id, monstrosIds));
    }///
}