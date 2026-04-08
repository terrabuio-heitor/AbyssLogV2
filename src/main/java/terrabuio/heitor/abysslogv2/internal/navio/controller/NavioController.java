package terrabuio.heitor.abysslogv2.internal.navio.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import terrabuio.heitor.abysslogv2.internal.navio.domain.Navio;
import terrabuio.heitor.abysslogv2.internal.navio.dto.request.NavioRequest;
import terrabuio.heitor.abysslogv2.internal.navio.dto.response.NavioResponse;
import terrabuio.heitor.abysslogv2.internal.navio.mapper.NavioMapper;
import terrabuio.heitor.abysslogv2.internal.navio.services.NavioService;

import java.util.List;

@RestController
@RequestMapping("/api/navios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NavioController {
    private final NavioService navioService;

//    @GetMapping
//    public List<Navio> listar() {
//        return navioService.listarTodos();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Navio> buscar(@PathVariable Long id) {
//        return ResponseEntity.ok(navioService.buscarPorId(id));
//    }
    @GetMapping
    public List<NavioResponse> listar() {
        return navioService.listarTodos()
                .stream()
                .map(NavioMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NavioResponse> buscar(@PathVariable Long id) {
        Navio navio = navioService.buscarPorId(id);
        return ResponseEntity.ok(NavioMapper.toResponse(navio));
    }

//    @PostMapping
//    public ResponseEntity<Navio> criar(@RequestBody @Valid Navio navio) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(navioService.salvar(navio));
//    }
    @PostMapping
    public NavioResponse criar(@RequestBody @Valid NavioRequest request){
        Navio navio = NavioMapper.toEntity(request);

        Navio salva = navioService.salvar(navio);

        return NavioMapper.toResponse(salva);
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
