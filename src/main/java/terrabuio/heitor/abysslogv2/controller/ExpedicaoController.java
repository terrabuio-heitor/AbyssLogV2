package terrabuio.heitor.abysslogv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import terrabuio.heitor.abysslogv2.domain.Expedicao;
import terrabuio.heitor.abysslogv2.services.ExpedicaoService;


import java.util.List;

@RestController
@RequestMapping("/api/expedicoes")
@RequiredArgsConstructor
public class ExpedicaoController {

    private final ExpedicaoService expedicaoService;

    @GetMapping
    public List<Expedicao> listar() {
        return expedicaoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expedicao> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(expedicaoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Expedicao> iniciar(@RequestBody @Valid Expedicao expedicao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expedicaoService.iniciar(expedicao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expedicao> atualizar(@PathVariable Long id, @RequestBody @Valid Expedicao expedicao) {
        return ResponseEntity.ok(expedicaoService.atualizar(id, expedicao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> encerrar(@PathVariable Long id) {
        expedicaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}