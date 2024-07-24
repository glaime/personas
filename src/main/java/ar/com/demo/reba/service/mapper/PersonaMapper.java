package ar.com.demo.reba.service.mapper;

import ar.com.demo.reba.dto.request.PersonaRequestDTO;
import ar.com.demo.reba.dto.response.PersonaResponseDTO;
import ar.com.demo.reba.entity.IdPersonaEntity;
import ar.com.demo.reba.entity.PaisEntity;
import ar.com.demo.reba.entity.PersonaEntity;
import ar.com.demo.reba.entity.TipoDocumentoEntity;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class PersonaMapper {

    PersonaMapper(){ }

    public static IdPersonaEntity buildIdPersonaEntity(String nroDoc, Long idTipoDoc, Long idPais){

        TipoDocumentoEntity tipoDocumento = new TipoDocumentoEntity();
        tipoDocumento.setId(idTipoDoc);


        PaisEntity pais = new PaisEntity();
        pais.setId(idPais);

        IdPersonaEntity idPersona = new IdPersonaEntity();
        idPersona.setNroDocumento(nroDoc);
        idPersona.setTipoDocumento(tipoDocumento);
        idPersona.setPais(pais);

        return idPersona;

    }
    public static PersonaEntity toEntity(PersonaRequestDTO persona, IdPersonaEntity idPersona){
        PersonaEntity personaEntity = new PersonaEntity();
        if(persona != null && idPersona != null){
            personaEntity.setIdPersona(idPersona);
            personaEntity.setNombre(persona.getNombre());
            personaEntity.setApellido(persona.getApellido());
            personaEntity.setFechaNacimiento(persona.getFechaNacimiento());
            personaEntity.setNroCel(persona.getNroCel());
            personaEntity.setNroAlt(persona.getNroAlt());
        }
        return personaEntity;
    }
    public static PersonaResponseDTO toDto(PersonaEntity entity){
        PersonaResponseDTO personaResponseDTO = new PersonaResponseDTO();
        if(entity != null){
            ModelMapper mapper = new ModelMapper();
            mapper.typeMap(PersonaEntity.class, PersonaResponseDTO.class).addMappings(mappr -> {
                mappr.map(src -> src.getIdPersona().getNroDocumento(), PersonaResponseDTO::setNroDocumento);
                mappr.map(src -> src.getIdPersona().getTipoDocumento().getDescipcion(), PersonaResponseDTO::setTipoDocumento);
                mappr.map(src -> src.getIdPersona().getPais().getDescipcion(), PersonaResponseDTO::setPais);
            });
            personaResponseDTO = mapper.map(entity, PersonaResponseDTO.class);
        }
        return personaResponseDTO;
    }

    public static List<PersonaResponseDTO> toDtoList(List<PersonaEntity> list){
        List<PersonaResponseDTO> result = new ArrayList<>();
        if(!list.isEmpty()){
            list.forEach( item -> result.add(PersonaMapper.toDto(item)));
        }
        return result;
    }

}
