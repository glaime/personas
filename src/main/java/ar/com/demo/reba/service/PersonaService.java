package ar.com.demo.reba.service;

import ar.com.demo.reba.dto.request.PersonaRequestDTO;
import ar.com.demo.reba.dto.response.PersonaResponseDTO;
import ar.com.demo.reba.exception.BusinessException;

import java.util.List;

public interface PersonaService {

    List<PersonaResponseDTO> getAll();

    PersonaResponseDTO getById(String nroDoc, Long idTipoDoc, Long idPais);

    PersonaResponseDTO create(PersonaRequestDTO body) throws BusinessException;

    PersonaResponseDTO update(PersonaRequestDTO body) throws BusinessException;

    void deleteById(String nroDoc, Long idTipoDoc, Long idPais) throws BusinessException;

}
