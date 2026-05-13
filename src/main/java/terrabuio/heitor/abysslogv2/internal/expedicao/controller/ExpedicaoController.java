package terrabuio.heitor.abysslogv2.internal.expedicao.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import terrabuio.heitor.abysslogv2.internal.evento.domain.Evento;
import terrabuio.heitor.abysslogv2.internal.evento.dto.request.EventoRequest;
import terrabuio.heitor.abysslogv2.internal.evento.dto.response.EventoResponse;
import terrabuio.heitor.abysslogv2.internal.evento.mapper.EventoMapper;
import terrabuio.heitor.abysslogv2.internal.expedicao.domain.Expedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.dto.request.ExpedicaoRequest;
import terrabuio.heitor.abysslogv2.internal.expedicao.dto.response.DiarioBordoResponse;
import terrabuio.heitor.abysslogv2.internal.expedicao.dto.response.ExpedicaoResponse;
import terrabuio.heitor.abysslogv2.internal.expedicao.mapper.ExpedicaoMapper;
import terrabuio.heitor.abysslogv2.internal.expedicao.services.ExpedicaoService;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.execucao.AtribuirEvento;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.execucao.IniciarExpedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.execucao.PausarExpedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.finalizacao.FinalizarExpedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.finalizacao.GerarDiarioDeBordo;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.finalizacao.InterromperExpedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.planejamento.ApagarExpedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.planejamento.AtualizarExpedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.planejamento.RegistrarExpedicao;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.preparacao.AtribuirNavio;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.preparacao.AtribuirTripulante;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.preparacao.RemoverTripulante;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expedicoes")
public class ExpedicaoController {
    private final ExpedicaoService crudBasico;

    //USECASES
    private final RegistrarExpedicao registrarExpedicao;
    private final IniciarExpedicao iniciarExpedicao;
    private final PausarExpedicao pausarExpedicao;
    private final FinalizarExpedicao finalizarExpedicao;
    private final InterromperExpedicao interromperExpedicao;
    private final AtribuirNavio atribuirNavio;
    private final AtribuirTripulante atribuirTripulante;
    private final RemoverTripulante removerTripulante;
    private final AtribuirEvento atribuirEvento;
    private final GerarDiarioDeBordo gerarDiarioDeBordo;
    private final ApagarExpedicao apagarExpedicao;
    private final AtualizarExpedicao atualizarExpedicao;

    //--Simples GET
    @Operation(summary = "Lista todas as expedições", description = "Retorna um log completo de todas as viagens marítimas registradas no AbyssLog")
    @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    @GetMapping
    public List<ExpedicaoResponse> listar() {
        return crudBasico.listarTodosResponse();
    }
    @Operation(summary = "Busca expedição por ID", description = "Retorna os detalhes de uma viagem específica através do seu identificador único.")
    @ApiResponse(responseCode = "200", description = "Expedição encontrada")
    @ApiResponse(responseCode = "404", description = "Registro da Expedição não encontrado")
    @GetMapping("/{id}")
    public ExpedicaoResponse listarPorID(@PathVariable Long id) {
        return crudBasico.buscarPorIdResponse(id);
    }


    //--O Patch
    @Operation(
            summary = "Atualiza campos específicos de uma expedição",
            description = "Permite alterar nome, capitão ou data de início de forma independente. Campos nulos no request não serão alterados no banco."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expedição atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou regra de cronologia violada"),
            @ApiResponse(responseCode = "404", description = "Expedição não encontrada")
    })
    @PatchMapping("/{id}")
    public ExpedicaoResponse atualizar(@PathVariable Long id, @RequestBody @Valid ExpedicaoRequest expedicaoRequest) {
        return atualizarExpedicao.executar(id, expedicaoRequest);
    }


    //--Get Diário de Bordo
    @Operation(summary = "Gera Diário de Bordo", description = "Compila todos os eventos, tripulantes e dados da jornada em um relatório de finalização.")
    @ApiResponse(responseCode = "200", description = "Diário gerado com sucesso")
    @GetMapping("/{id}/gerar-diario-de-bordo")
    public DiarioBordoResponse gerarDiario(@PathVariable Long id){
        return gerarDiarioDeBordo.executar(id);
    }

    //Máquina de Estados

    @Operation(summary = "Inicia uma Expedição", description = "Muda o status dela para 'Em Mar' e deixa novas operações serem realizadas, como o cadastro de Eventos")
    @ApiResponse(responseCode = "204", description = "Expedição iniciada")
    @PostMapping("/{id}/iniciar")
    public ResponseEntity<Void> iniciar(@PathVariable Long id) {
        iniciarExpedicao.executar(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Pausa a Expedição", description = "Suspende temporariamente o progresso da viagem.")
    @ApiResponse(responseCode = "204", description = "Expedição pausada")
    @PostMapping("/{id}/pausar")
    public ResponseEntity<Void> pausar(@PathVariable Long id) {
        pausarExpedicao.executar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Finaliza Expedição", description = "Encerra a viagem com sucesso, mudando o status para 'FINALIZADA'.")
    @ApiResponse(responseCode = "204", description = "Expedição encerrada")
    @PostMapping("/{id}/finalizar")
    public ResponseEntity<Void> finalizar(@PathVariable Long id) {
        finalizarExpedicao.executar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Interrompe Expedição", description = "Encerra a viagem abruptamente devido a um evento crítico (ex: naufrágio).")
    @ApiResponse(responseCode = "204", description = "Expedição interrompida")
    @PostMapping("/{id}/interromper/{evId}")
    public ResponseEntity<Void> interrompar(@PathVariable Long id,  @PathVariable Long evId) {
        interromperExpedicao.executar(id,evId);
        return ResponseEntity.noContent().build();
    }
    //--Preparação
    @Operation(summary = "Registra nova expedição", description = "Cria o planejamento inicial de uma viagem (status inicial: PLANEJAMENTO).")
    @ApiResponse(responseCode = "201", description = "Expedição criada com sucesso")
    @PostMapping("/registrar")
    public ResponseEntity<ExpedicaoResponse> registrarExpedicao(@RequestBody ExpedicaoRequest expedicaoRequest) {
        Expedicao ex = registrarExpedicao.iniciar(expedicaoRequest);
        return ResponseEntity.status(201).body(ExpedicaoMapper.toResponse(ex));
    }

    @Operation(summary = "Atribui um Navio", description = "Vincula um navio disponível à expedição antes de sua partida.")
    @ApiResponse(responseCode = "204", description = "Navio atribuído com sucesso")
    @ApiResponse(responseCode = "400", description = "Navio ou Expedição em estado inválido")
    @PostMapping("/{id}/atribuir-navio/{navioId}")
    public ResponseEntity<Void> atribuirNavio(@PathVariable Long id, @PathVariable Long navioId) {
        atribuirNavio.executar(id, navioId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Adiciona Tripulante", description = "Insere um novo membro na tripulação da expedição.")
    @ApiResponse(responseCode = "204", description = "Tripulante adicionado")
    @ApiResponse(responseCode = "400", description = "Tripulante já está vinculado á uma expedição")
    @PatchMapping("/{id}/atribuir-tripulante/{idTripulante}")
    public ResponseEntity<Void> atribuirTripulante(@PathVariable Long id, @PathVariable Long idTripulante){
        atribuirTripulante.executar(id, idTripulante);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove Tripulante", description = "Remove um membro específico da tripulação atual.")
    @ApiResponse(responseCode = "204", description = "Tripulante removido")
    @ApiResponse(responseCode = "400", description = "Remoção não permitida no estado atual do Tripulante, ou Tripulante não está no Navio")
    @PatchMapping("/{id}/remover-tripulante/{idTripulante}")
    public ResponseEntity<Void> removerTripulante(@PathVariable Long id, @PathVariable Long idTripulante){
        removerTripulante.executar(id, idTripulante);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Registra um Evento", description = "Adiciona um acontecimento (tempestade, combate, descoberta) ao log da expedição ativa.")
    @ApiResponse(responseCode = "201", description = "Evento registrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados do evento inválidos ou expedição não está 'Em Mar'")
    @PostMapping("/{id}/registrar-evento")
    public ResponseEntity<EventoResponse> registrarEvento(@PathVariable Long id, @Valid @RequestBody EventoRequest eventoRequest) {
        Evento ev = atribuirEvento.executar(id, eventoRequest);
        return ResponseEntity.ok(EventoMapper.toResponse(ev));
    }

    @Operation(summary = "Apaga uma Expedição", description = "Remove ela completamente dos registros, mas para isso deve ela não deve ter iniciado")
    @ApiResponse(responseCode = "204", description = "Expedição apagada completamente")
    @ApiResponse(responseCode = "400", description = "Não se pode apagar um registro histórico, essa viagem já teve ")
    @DeleteMapping("/{id}/remover")
    public ResponseEntity<Void> apagarExpedicao(@PathVariable Long id) {
        apagarExpedicao.executar(id);
        return ResponseEntity.noContent().build();
    }
}
