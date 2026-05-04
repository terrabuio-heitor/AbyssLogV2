package terrabuio.heitor.abysslogv2.internal.expedicao.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.execucao.IniciarExpedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.execucao.PausarExpedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.finalizacao.FinalizarExpedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.finalizacao.InterromperExpedicao;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expedicoes/")
public class ControllerPrincipal {
    private final ExpedicaoService crudBasico;

    //USECASES
    private final IniciarExpedicao iniciarExpedicao;
    private final PausarExpedicao pausarExpedicao;
    private final FinalizarExpedicao finalizarExpedicao;
    private final InterromperExpedicao interromperExpedicao;

    @PostMapping("/{id}/iniciar")
    public ResponseEntity<Void> iniciar(@PathVariable Long id) {
        // O Controller apenas delega a responsabilidade
        iniciarExpedicao.executar(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{id}/pausar")
    public ResponseEntity<Void> pausar(@PathVariable Long id) {
        pausarExpedicao.executar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<Void> finalizar(@PathVariable Long id) {
        finalizarExpedicao.executar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/interromper/{evId}")
    public ResponseEntity<Void> interrompar(@PathVariable Long id,  @PathVariable Long evId) {
        interromperExpedicao.executar(id,evId);
        return ResponseEntity.noContent().build();
    }
}
