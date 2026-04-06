package terrabuio.heitor.abysslogv2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import terrabuio.heitor.abysslogv2.domain.TripulanteExpedicao;
import terrabuio.heitor.abysslogv2.services.TripulanteExpedicaoService;

@RestController
@RequestMapping("/api/expedicoes/tripulantes")
@RequiredArgsConstructor
public class TripulanteExpedicaoController {
    private final TripulanteExpedicaoService service;

    @PostMapping("/vincular")
    public ResponseEntity<TripulanteExpedicao> vincular(@RequestBody TripulanteExpedicao vinculo) {
        return ResponseEntity.ok(service.vincular(vinculo));
    }

    @PutMapping("/desvincular/{id}")
    public ResponseEntity<TripulanteExpedicao> desvincular(@PathVariable Long id) {
        return ResponseEntity.ok(service.desvincular(id));
    }
}