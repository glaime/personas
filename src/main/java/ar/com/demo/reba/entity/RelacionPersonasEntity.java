package ar.com.demo.reba.entity;

import javax.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "RELACION_PERSONA")
@EqualsAndHashCode
public class RelacionPersonasEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RELACION_PERSONA")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name="ID_TIPO_DOC", referencedColumnName="ID_TIPO_DOC"),
            @JoinColumn(name="ID_PAIS", referencedColumnName="ID_PAIS"),
            @JoinColumn(name="NRO_DOCUMENTO", referencedColumnName="NRO_DOCUMENTO")
    })
    private PersonaEntity personaPadre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RELACION", nullable = false)
    private TipoRelacionEntity relacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name="ID_TIPO_DOC_2", referencedColumnName="ID_TIPO_DOC"),
            @JoinColumn(name="ID_PAIS_2", referencedColumnName="ID_PAIS"),
            @JoinColumn(name="NRO_DOCUMENTO_2", referencedColumnName="NRO_DOCUMENTO")
    })
    private PersonaEntity personaHijo;

}
