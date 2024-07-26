package ar.com.demo.reba.repository;

import ar.com.demo.reba.entity.PersonaEntity;
import ar.com.demo.reba.entity.RelacionPersonasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelacionPersonaRepository extends JpaRepository<RelacionPersonasEntity, Long> {

    RelacionPersonasEntity findByPersonaHijo(PersonaEntity personaHijo);

    List<RelacionPersonasEntity> findByPersonaPadre(PersonaEntity personaPadre);

}
