package terrabuio.heitor.abysslogv2.internal.monstro.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import terrabuio.heitor.abysslogv2.internal.monstro.services.MonstroService;
import terrabuio.heitor.abysslogv2.internal.monstro.domain.Monstro;

import java.util.List;

@RestController
@RequestMapping("/api/monstros")
@RequiredArgsConstructor
public class MonstroController {
    private final MonstroService monstroService;

    @GetMapping
    public List<Monstro> listar() {
        return monstroService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Monstro> iniciar(@RequestBody @Valid Monstro monstro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(monstroService.criar(monstro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Monstro> atualizar(@PathVariable Long id, @RequestBody @Valid Monstro monstro) {
        return ResponseEntity.ok(monstroService.atualizar(id, monstro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        monstroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
