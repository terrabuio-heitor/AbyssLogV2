package terrabuio.heitor.abysslogv2.internal.expedicao.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.dto.request.ExpedicaoRequest;
import terrabuio.heitor.abysslogv2.internal.expedicao.dto.response.ExpedicaoResponse;
import terrabuio.heitor.abysslogv2.internal.expedicao.mapper.ExpedicaoMapper;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.execucao.IniciarExpedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.execucao.PausarExpedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.finalizacao.FinalizarExpedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.finalizacao.InterromperExpedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.planejamento.RegistrarExpedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.preparacao.AtribuirNavio;
import terrabuio.heitor.abysslogv2.internal.navio.domain.Navio;
import terrabuio.heitor.abysslogv2.internal.navio.services.NavioService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expedicoes/")
public class ExpedicaoController {
    private final ExpedicaoService crudBasico;
    private final NavioService navioService;

    //USECASES
    private final RegistrarExpedicao registrarExpedicao;
    private final IniciarExpedicao iniciarExpedicao;
    private final PausarExpedicao pausarExpedicao;
    private final FinalizarExpedicao finalizarExpedicao;
    private final InterromperExpedicao interromperExpedicao;


    private final AtribuirNavio atribuirNavio;


    @Operation(summary = "Inicia uma Expedição", description = "Muda o status dela para 'Em Mar' e deixa novas operações serem realizadas, como o cadastro de Eventos")
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

    @Operation(summary = "Lista todas as expedições", description = "Retorna um log completo de todas as viagens marítimas registradas no AbyssLog")
    @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    @GetMapping
    public List<ExpedicaoResponse> listar() {
        return crudBasico.listarTodosResponse();
    }

    @PostMapping("/registrar")
    public ResponseEntity<ExpedicaoResponse> registrarExpedicao(@RequestBody ExpedicaoRequest expedicaoRequest) {
        Expedicao ex = registrarExpedicao.iniciar(expedicaoRequest);
        return ResponseEntity.ok(ExpedicaoMapper.toResponse(ex));
    }

    @PostMapping("/{id}/atribuirNavio/{navioId}")
    public ResponseEntity<Void> atribuirNavio(@PathVariable long id, @PathVariable long navioId) {
        atribuirNavio.executar(id, navioId);
        return ResponseEntity.noContent().build();
    }

}
