package terrabuio.heitor.abysslogv2.internal.expedicao.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import terrabuio.heitor.abysslogv2.internal.expedicao.dto.request.ExpedicaoRequest;
import terrabuio.heitor.abysslogv2.internal.expedicao.dto.response.ExpedicaoResponse;
import terrabuio.heitor.abysslogv2.internal.expedicao.mapper.ExpedicaoMapper;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.navio.domain.Navio;
import terrabuio.heitor.abysslogv2.internal.navio.services.NavioService;


import java.util.List;

@RestController
@RequestMapping("/api/legacy/expedicoes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExpedicaoControllerLegacy {

    private final ExpedicaoService expedicaoService;
    private final NavioService navioService;

    //CRUD FUNCIONAL E Básico

    @Deprecated
    @GetMapping
    public List<ExpedicaoResponse> listar() {
        return expedicaoService.listarTodos()
                .stream()
                .map(ExpedicaoMapper::toResponse)
                .toList();
    }
    @Deprecated
    @GetMapping("/{id}")
    public ResponseEntity<ExpedicaoResponse> buscarPorId(@PathVariable Long id) {
        Expedicao expedicao = expedicaoService.buscarPorId(id);
        return ResponseEntity.ok(ExpedicaoMapper.toResponse(expedicao));
    }
    @Deprecated
    @PostMapping
    public ExpedicaoResponse iniciar(@RequestBody @Valid ExpedicaoRequest request){
        Navio navio = navioService.buscarPorId(request.idNavio());
        
        Expedicao expedicao = ExpedicaoMapper.toEntity(request, navio);

        Expedicao salva = expedicaoService.iniciar(expedicao);
        
        return ExpedicaoMapper.toResponse(salva);
    }
    @Deprecated
    @PutMapping("/{id}")
    public ResponseEntity<Expedicao> atualizar(@PathVariable Long id, @RequestBody @Valid Expedicao expedicao) {
        return ResponseEntity.ok(expedicaoService.atualizar(id, expedicao));
    }
    @Deprecated
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> encerrar(@PathVariable Long id) {
        expedicaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}