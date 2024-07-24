package ar.com.demo.reba.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "PERSONA")
public class PersonaEntity implements Serializable {

    @EmbeddedId
    private IdPersonaEntity idPersona;

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
