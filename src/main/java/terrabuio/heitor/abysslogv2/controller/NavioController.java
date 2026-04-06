package terrabuio.heitor.abysslogv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import terrabuio.heitor.abysslogv2.domain.Navio;
import terrabuio.heitor.abysslogv2.services.NavioService;

import java.util.List;

@RestController
@RequestMapping("/api/navios")
@RequiredArgsConstructor
public class NavioController {
    private final NavioService navioService;

    @GetMapping
    public List<Navio> listar() {
        return navioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Navio> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(navioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Navio> criar(@RequestBody @Valid Navio navio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(navioService.salvar(navio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Navio> atualizar(@PathVariable Long id, @RequestBody @Valid Navio navio) {
        return ResponseEntity.ok(navioService.atualizar(id, navio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        navioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
