package terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import terrabuio.heitor.abysslogv2.internal.expedicao.usecases.preparacao.RemoverTripulante;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.dto.request.TripulanteExpedicaoRequest;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.dto.response.TripulanteExpedicaoResponse;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.mapper.TripulanteExpedicaoMapper;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.services.TripulanteExpedicaoService;
import terrabuio.heitor.abysslogv2.internal.tripulanteExpedicao.domain.TripulanteExpedicao;

import java.util.List;

@RestController
@RequestMapping("/api/expedicoes/tripulantes")
@RequiredArgsConstructor
public class TripulanteExpedicaoController {
    private final TripulanteExpedicaoService service;
    private final RemoverTripulante removerTripulante;

    //GET Básico Read CRUD
    @GetMapping
    public List<TripulanteExpedicaoResponse> listar(){
        return service.listar()
                .stream()
                .map(TripulanteExpedicaoMapper::toResponse)
                .toList();
    }

    //Inicio dos USECASES ou casos de usos
    @PostMapping("/vincular")
    public TripulanteExpedicaoResponse vincular(@RequestBody TripulanteExpedicaoRequest request){
        return service.vincular(request);
    }


    @PatchMapping("/{id}/finalizar")
    public TripulanteExpedicaoResponse finalizar(@PathVariable Long id) {
        TripulanteExpedicao vinculo = service.desvincular(id);

        return TripulanteExpedicaoMapper.toResponse(vinculo);
    }

    @DeleteMapping("/tripulante/{idTripulante}/expedicao/{idExpedicao}")
    public ResponseEntity<Void> remover(@PathVariable Long idTripulante, @PathVariable Long idExpedicao) {
        removerTripulante.executar(idTripulante, idExpedicao);
        return ResponseEntity.noContent().build();
    }
}