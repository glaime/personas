package ar.com.demo.reba.repository;

import ar.com.demo.reba.entity.IdPersonaEntity;
import ar.com.demo.reba.entity.PaisEntity;
import ar.com.demo.reba.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<PersonaEntity, IdPersonaEntity> {

    long countByPais(PaisEntity pais);

}
