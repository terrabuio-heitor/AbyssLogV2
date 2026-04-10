package terrabuio.heitor.abysslogv2.internal.tripulante.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import terrabuio.heitor.abysslogv2.internal.tripulante.dto.request.TripulanteRequest;
import terrabuio.heitor.abysslogv2.internal.tripulante.dto.response.TripulanteResponse;
import terrabuio.heitor.abysslogv2.internal.tripulante.mapper.TripulanteMapper;
import terrabuio.heitor.abysslogv2.internal.tripulante.services.TripulanteService;
import terrabuio.heitor.abysslogv2.internal.tripulante.domain.Tripulante;

import java.util.List;

@RestController
@RequestMapping("api/tripulantes")
@RequiredArgsConstructor

public class TripulanteController {
    private final TripulanteService tripulanteService;

    //CRUD FUNCIONAL E Básico
    @GetMapping
    public List<TripulanteResponse> listar() {
        return tripulanteService.listarTodos()
                .stream()
                .map(TripulanteMapper::toResponse)
                .toList();
    }
    @PostMapping
    public  TripulanteResponse salvar(@Valid @RequestBody TripulanteRequest request){
        Tripulante tripulante = TripulanteMapper.toEntity(request);
        Tripulante salvo = tripulanteService.salvar(tripulante);
        return TripulanteMapper.toResponse(salvo);
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
