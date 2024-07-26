package ar.com.demo.reba.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RelacionPersonasResponseDTO {

    private String nombrePadre;
    private String apellidoPadre;
    private String nroDocPadre;
    private String nombreHijo;
    private String apellidoHijo;
    private String nroDocHijo;

}
