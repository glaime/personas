package ar.com.demo.reba.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonaResponseDTO {

    private String nombre;
    private String apellido;
    private String tipoDocumento;
    private String nroDocumento;
    private String pais;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date fechaNacimiento;
    private String nroCel;
    private String nroAlt;

}
