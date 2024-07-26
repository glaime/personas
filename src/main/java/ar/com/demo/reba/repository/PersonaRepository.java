package ar.com.demo.reba.repository;

import ar.com.demo.reba.entity.IdPersonaEntity;
import ar.com.demo.reba.entity.PaisEntity;
import ar.com.demo.reba.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<PersonaEntity, IdPersonaEntity> {

    long countByPais(PaisEntity pais);

    @Query(value = "INSERT INTO persona (nombre , apellido , id_tipo_doc , nro_documento , id_pais , fecha_nac , nro_cel , nro_alt)" +
            " VALUES (:nombre, :apellido, :idTipoDoc, :nroDocumento, :idPais, :fechaNacimiento, :nroCel, :nroAlt)",
            nativeQuery = true)
    @Modifying
    Integer createPersona(@Param("nombre") String nombre, @Param("apellido") String apellido, @Param("idTipoDoc") Long idTipoDoc, @Param("nroDocumento") String nroDocumento,
                          @Param("idPais") Long idPais, @Param("fechaNacimiento") Date fechaNacimiento, @Param("nroCel") String nroCel, @Param("nroAlt") String nroAlt);

    @Query(value = "UPDATE persona" +
            " SET nombre = :nombre," +
            " apellido = :apellido," +
            " fecha_nac = :fechaNacimiento," +
            " nro_cel = :nroCel," +
            " nro_alt = :nroAlt" +
            " WHERE" +
            " id_tipo_doc = :idTipoDoc" +
            " AND nro_documento = :nroDocumento" +
            " AND id_pais = :idPais",
            nativeQuery = true)
    @Modifying
    Integer updatePersona(@Param("nombre") String nombre, @Param("apellido") String apellido, @Param("fechaNacimiento") Date fechaNacimiento,
                          @Param("nroCel") String nroCel, @Param("nroAlt") String nroAlt, @Param("idTipoDoc") Long idTipoDoc,
                          @Param("nroDocumento") String nroDocumento, @Param("idPais") Long idPais);

    @Query(value = "DELETE FROM persona WHERE id_tipo_doc = :idTipoDoc AND nro_documento = :nroDocumento AND id_pais = :idPais",
    nativeQuery = true)
    @Modifying
    Integer deletePersona(@Param("nroDocumento") String nroDocumento, @Param("idTipoDoc") Long idTipoDoc, @Param("idPais") Long idPais);
}
