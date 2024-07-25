package ar.com.demo.reba.controller;

import ar.com.demo.reba.dto.response.ErrorResponseDTO;
import ar.com.demo.reba.service.RelacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/relaciones")
@Slf4j
@Tag(name = "RelacionesController")
public class RelacionesController {

    private final RelacionService service;

    public RelacionesController(RelacionService service) {
        super();
        this.service = service;
    }


    @GetMapping("/{nroDoc1}/{idTipoDoc1}/{idPais1}/{nroDoc2}/{idTipoDoc2}/{idPais2}")
    @Operation(summary = "Obtener relacion entre 2 personas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<Object> getRelacion(@PathVariable("nroDoc1") String nroDoc1, @PathVariable("idTipoDoc1") Long idTipoDoc1, @PathVariable("idPais1") Long idPais1,
                                              @PathVariable("nroDoc2") String nroDoc2, @PathVariable("idTipoDoc2") Long idTipoDoc2, @PathVariable("idPais2") Long idPais2){
        try{
            HashMap<String, String> response = new HashMap<>();
            String rta = this.service.getRelacion(nroDoc1, idTipoDoc1, idPais1, nroDoc2, idTipoDoc2, idPais2);
            response.put("resultado", rta);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex){
            log.error("Ocurrió un error al obtener relacion entre 2 personas");
            return new ResponseEntity<>(
                    ErrorResponseDTO.builder().codigo(500).mensaje(ex.getMessage()).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
