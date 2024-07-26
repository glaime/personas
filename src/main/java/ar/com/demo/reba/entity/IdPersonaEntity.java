package ar.com.demo.reba.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class IdPersonaEntity implements Serializable {

    private TipoDocumentoEntity tipoDocumento;

    private PaisEntity pais;

    private String nroDocumento;

}
