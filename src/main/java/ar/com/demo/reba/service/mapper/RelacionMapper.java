package ar.com.demo.reba.service.mapper;

import ar.com.demo.reba.dto.response.RelacionPersonasResponseDTO;
import ar.com.demo.reba.entity.RelacionPersonasEntity;
import org.modelmapper.ModelMapper;

public class RelacionMapper {

    RelacionMapper(){ }

    public static RelacionPersonasResponseDTO toDto(RelacionPersonasEntity relacionPersonas){
        RelacionPersonasResponseDTO dto = new RelacionPersonasResponseDTO();
        if(relacionPersonas != null){
            ModelMapper mapper = new ModelMapper();
            mapper.typeMap(RelacionPersonasEntity.class, RelacionPersonasResponseDTO.class).addMappings(mappr -> {
                mappr.map(src -> src.getPersonaPadre().getNombre(), RelacionPersonasResponseDTO::setNombrePadre);
                mappr.map(src -> src.getPersonaPadre().getApellido(), RelacionPersonasResponseDTO::setApellidoPadre);
                mappr.map(src -> src.getPersonaPadre().getNroDocumento(), RelacionPersonasResponseDTO::setNroDocPadre);
                mappr.map(src -> src.getPersonaHijo().getNombre(), RelacionPersonasResponseDTO::setNombreHijo);
                mappr.map(src -> src.getPersonaHijo().getApellido(), RelacionPersonasResponseDTO::setApellidoHijo);
                mappr.map(src -> src.getPersonaHijo().getNroDocumento(), RelacionPersonasResponseDTO::setNroDocHijo);
            });
            dto = mapper.map(relacionPersonas, RelacionPersonasResponseDTO.class);
        }
        return dto;
    }

}
