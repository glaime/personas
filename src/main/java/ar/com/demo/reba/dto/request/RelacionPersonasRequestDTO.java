package ar.com.demo.reba.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelacionPersonasRequestDTO {

    @NotNull(message = "Parámetro idTipoDocPadre requerido")
    private Long idTipoDocPadre;

    @NotNull(message = "Parámetro idPaisPadre requerido")
    private Long idPaisPadre;

    @NotNull(message = "Parámetro idTipoDocHijo requerido")
    private Long idTipoDocHijo;

    @NotNull(message = "Parámetro idPaisHijo requerido")
    private Long idPaisHijo;

}
