package ar.com.demo.reba.service;

import ar.com.demo.reba.dto.request.PersonaRequestDTO;
import ar.com.demo.reba.dto.request.RelacionPersonasRequestDTO;
import ar.com.demo.reba.dto.response.PersonaResponseDTO;
import ar.com.demo.reba.dto.response.PorcentajeResponseDTO;
import ar.com.demo.reba.dto.response.RelacionPersonasResponseDTO;
import ar.com.demo.reba.entity.PersonaEntity;
import ar.com.demo.reba.exception.BusinessException;

import java.util.List;
import java.util.Optional;

public interface PersonaService {

    List<PersonaResponseDTO> getAll();

    PersonaResponseDTO getById(String nroDoc, Long idTipoDoc, Long idPais);

    PersonaResponseDTO create(PersonaRequestDTO body) throws BusinessException;

    PersonaResponseDTO update(PersonaRequestDTO body) throws BusinessException;

    void deleteById(String nroDoc, Long idTipoDoc, Long idPais) throws BusinessException;

    RelacionPersonasResponseDTO createRelacionPersonas(String nroDocPadre, String nroDocHijo, RelacionPersonasRequestDTO body) throws BusinessException;

    Optional<PersonaEntity> getPersonaById(String nroDoc, Long idTipoDoc, Long idPais);

    List<PorcentajeResponseDTO> getStats();

}
