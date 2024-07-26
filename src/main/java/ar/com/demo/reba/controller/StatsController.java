package ar.com.demo.reba.controller;

import ar.com.demo.reba.dto.response.ErrorResponseDTO;
import ar.com.demo.reba.dto.response.PersonaResponseDTO;
import ar.com.demo.reba.dto.response.PorcentajeResponseDTO;
import ar.com.demo.reba.service.PersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stats")
@Slf4j
@Tag(name = "StatsController")
public class StatsController {

    private final PersonaService service;

    public StatsController(PersonaService service) {
        super();
        this.service = service;
    }

    @GetMapping("/")
    @Operation(summary = "Obtener porcentaje de personas por país")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PersonaResponseDTO.class))))
    })
    public ResponseEntity<Object> getStats(){
        log.debug("Petición para obtener porcentaje de personas por país.");
        List<PorcentajeResponseDTO> result;
        try{
            result = this.service.getStats();
            return ResponseEntity.ok(result);
        } catch (Exception ex){
            log.error("Ocurrió un error al obtener porcentaje de personas por país.");
            return new ResponseEntity<>(
                    ErrorResponseDTO.builder().codigo(500).mensaje(ex.getMessage()).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
