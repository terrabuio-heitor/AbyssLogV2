package terrabuio.heitor.abysslogv2.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import terrabuio.heitor.abysslogv2.domain.Tripulante;
import terrabuio.heitor.abysslogv2.services.TripulanteService;

import java.util.List;

@RestController
@RequestMapping("api/tripulantes")
@RequiredArgsConstructor

public class TripulanteController {
    private final TripulanteService tripulanteService;

    @GetMapping
    public List<Tripulante> listar() {
        return tripulanteService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Tripulante> iniciar(@RequestBody @Valid Tripulante tripulante) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tripulanteService.salvar(tripulante));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tripulante> atualizar(@PathVariable Long id, @RequestBody @Valid Tripulante tripulante) {
        return ResponseEntity.ok(tripulanteService.atualizar(id, tripulante));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tripulanteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
