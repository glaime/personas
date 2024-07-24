package ar.com.demo.reba.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonaRequestDTO {

    @NotNull(message = "Parámetro idTipoDoc requerido")
    private Long idTipoDoc;

    @NotNull(message = "Parámetro idPais requerido")
    private Long idPais;

    @NotBlank(message = "Parámetro nroDocumento requerido")
    private String nroDocumento;

    @NotBlank(message = "Parámetro nombre requerido")
    private String nombre;

    @NotBlank(message = "Parámetro apellido requerido")
    private String apellido;

    @NotNull(message = "Parámetro fechaNacimiento requerido")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date fechaNacimiento;

    @NotBlank(message = "Parámetro nroCel requerido")
    private String nroCel;

    private String nroAlt;

}
