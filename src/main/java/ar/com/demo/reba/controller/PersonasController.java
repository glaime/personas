package ar.com.demo.reba.controller;

import ar.com.demo.reba.dto.request.RelacionPersonasRequestDTO;
import ar.com.demo.reba.dto.response.PersonaResponseDTO;
import ar.com.demo.reba.dto.response.ErrorResponseDTO;
import ar.com.demo.reba.dto.request.PersonaRequestDTO;
import ar.com.demo.reba.dto.response.RelacionPersonasResponseDTO;
import ar.com.demo.reba.exception.BusinessException;
import ar.com.demo.reba.service.PersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/personas")
@Slf4j
@Tag(name = "PersonasController")
public class PersonasController {

    private static final String NOTFOUND = "Persona no encontrada";
    private final PersonaService service;

    public PersonasController(PersonaService service) {
        super();
        this.service = service;
    }

    @GetMapping("/")
    @Operation(summary = "Obtener listado de personas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PersonaResponseDTO.class))))
    })
    public ResponseEntity<Object> getAll(){
        log.debug("Petición para obtener listado de personas.");
        List<PersonaResponseDTO> result;
        try{
            result = service.getAll();
            return ResponseEntity.ok(result);
        } catch (Exception ex){
            return new ResponseEntity<>(
                    ErrorResponseDTO.builder().codigo(500).mensaje(ex.getMessage()).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{nroDoc}/{idTipoDoc}/{idPais}")
    @Operation(summary = "Buscar persona por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Persona not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<Object> getById(@PathVariable("nroDoc") String nroDoc, @PathVariable("idTipoDoc") Long idTipoDoc, @PathVariable("idPais") Long idPais){
        log.debug("Petición para buscar persona por ID");
        PersonaResponseDTO persona;
        try{
            persona = service.getById(nroDoc, idTipoDoc, idPais);
            if(persona != null && persona.getNroDocumento() == null){
                return new ResponseEntity<>(ErrorResponseDTO.builder().codigo(404).mensaje(NOTFOUND).build(), HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(persona);
        } catch (Exception ex){
            log.error("Ocurrió un error al buscar persona por ID");
            return new ResponseEntity<>(
                    ErrorResponseDTO.builder().codigo(500).mensaje(ex.getMessage()).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    @Operation(summary = "Insertar persona")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<Object> create(@Valid @RequestBody PersonaRequestDTO body){
        log.debug("Petición para insertar persona");
        PersonaResponseDTO personaCreated;
        try{
            personaCreated = this.service.create(body);
            return new ResponseEntity<>(personaCreated, HttpStatus.OK);
        } catch (BusinessException ex){
            log.error("Ocurrió un error al intentar insertar persona");
            return new ResponseEntity<>(
                    ErrorResponseDTO.builder().codigo(ex.getCode()).mensaje(ex.getMessage()).build(),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception ex){
            log.error("Ocurrió un error al intentar insertar persona");
            return new ResponseEntity<>(
                    ErrorResponseDTO.builder().codigo(500).mensaje(ex.getMessage()).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    @Operation(summary = "Actualizar una persona existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<Object> update(@Valid @RequestBody PersonaRequestDTO body){
        log.debug("Petición para actualizar una persona existente");
        PersonaResponseDTO updated;
        try{
            updated = this.service.update(body);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (BusinessException ex){
            log.error("Ocurrió un error al intentar actualizar persona");
            return new ResponseEntity<>(
                    ErrorResponseDTO.builder().codigo(ex.getCode()).mensaje(ex.getMessage()).build(),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception ex){
            log.error("Ocurrió un error al actualizar persona");
            return new ResponseEntity<>(
                    ErrorResponseDTO.builder().codigo(500).mensaje(ex.getMessage()).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{nroDoc}/{idTipoDoc}/{idPais}")
    @Operation(summary = "Eliminar persona")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<Object> deleteById(@PathVariable("nroDoc") String nroDoc, @PathVariable("idTipoDoc") Long idTipoDoc, @PathVariable("idPais") Long idPais){
        log.debug("Petición para eliminar persona");
        try{
            this.service.deleteById(nroDoc, idTipoDoc, idPais);
            HashMap<String, String> result =  new HashMap<>();
            result.put("respuesta", "Operación exitosa");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (BusinessException ex){
            log.error("Ocurrió un error al intentar eliminar persona");
            return new ResponseEntity<>(
                    ErrorResponseDTO.builder().codigo(ex.getCode()).mensaje(ex.getMessage()).build(),
                    HttpStatus.NOT_FOUND);
        } catch (Exception ex){
            log.error("Ocurrió un error al intentar eliminar persona");
            return new ResponseEntity<>(
                    ErrorResponseDTO.builder().codigo(500).mensaje(ex.getMessage()).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Insertar relacion persona - padre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping("/{nroDocPadre}/padre/{nroDocHijo}")
    public ResponseEntity<Object> createRelacionPersonas(@PathVariable("nroDocPadre") String nroDocPadre,
                                                         @PathVariable("nroDocHijo") String nroDocHijo,
                                                         @Valid @RequestBody RelacionPersonasRequestDTO body){
        log.debug("Petición para insertar relacion persona - padre");
        RelacionPersonasResponseDTO relacionCreated = new RelacionPersonasResponseDTO();
        try {
            relacionCreated = this.service.createRelacionPersonas(nroDocPadre, nroDocHijo, body);
            return new ResponseEntity<>(relacionCreated, HttpStatus.OK);
        } catch (BusinessException ex){
        log.error("Ocurrió un error al intentar insertar relacion persona - padre");
        return new ResponseEntity<>(
                ErrorResponseDTO.builder().codigo(ex.getCode()).mensaje(ex.getMessage()).build(),
                HttpStatus.BAD_REQUEST);
        } catch (Exception ex){
            log.error("Ocurrió un error al intentar insertar relacion persona - padre");
            return new ResponseEntity<>(
                    ErrorResponseDTO.builder().codigo(500).mensaje(ex.getMessage()).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
