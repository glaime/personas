package ar.com.demo.reba.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "PERSONA")
@IdClass(IdPersonaEntity.class)
@EqualsAndHashCode
public class PersonaEntity implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_DOC", nullable = false)
    private TipoDocumentoEntity tipoDocumento;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PAIS", nullable = false)
    private PaisEntity pais;

    @Id
    @Column(name = "NRO_DOCUMENTO", nullable = false, length = 50)
    private String nroDocumento;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "APELLIDO", nullable = false, length = 100)
    private String apellido;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "FECHA_NAC", nullable = false)
    private Date fechaNacimiento;

    @Column(name = "NRO_CEL", nullable = false, length = 30)
    private String nroCel;

    @Column(name = "NRO_ALT", length = 30)
    private String nroAlt;

}
