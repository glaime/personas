package ar.com.demo.reba.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class IdPersonaEntity implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_DOC", nullable = false)
    private TipoDocumentoEntity tipoDocumento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PAIS", nullable = false)
    private PaisEntity pais;

    @Column(name = "NRO_DOCUMENTO", nullable = false, length = 50)
    private String nroDocumento;

}
