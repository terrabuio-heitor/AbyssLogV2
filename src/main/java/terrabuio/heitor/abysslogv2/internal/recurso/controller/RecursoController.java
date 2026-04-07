package terrabuio.heitor.abysslogv2.internal.recurso.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import terrabuio.heitor.abysslogv2.internal.recurso.domain.Recurso;
import terrabuio.heitor.abysslogv2.internal.recurso.services.RecursoService;

import java.util.List;

@RestController
@RequestMapping("api/recursos")
@RequiredArgsConstructor
public class RecursoController {
    private final RecursoService recursoService;

    @GetMapping
    public List<Recurso> listar() {
        return recursoService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Recurso> iniciar(@RequestBody @Valid Recurso recurso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recursoService.salvar(recurso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recurso> atualizar(@PathVariable Long id, @RequestBody @Valid Recurso recurso) {
        return ResponseEntity.ok(recursoService.atualizar(id, recurso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        recursoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
