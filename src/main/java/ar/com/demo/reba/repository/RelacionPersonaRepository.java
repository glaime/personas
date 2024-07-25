package ar.com.demo.reba.repository;

import ar.com.demo.reba.entity.PersonaEntity;
import ar.com.demo.reba.entity.RelacionPersonasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelacionPersonaRepository extends JpaRepository<RelacionPersonasEntity, Long> {

    RelacionPersonasEntity findByPersonaHijo(PersonaEntity personaHijo);

}
