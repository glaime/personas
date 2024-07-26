package ar.com.demo.reba.service.impl;

import ar.com.demo.reba.entity.PersonaEntity;
import ar.com.demo.reba.entity.RelacionPersonasEntity;
import ar.com.demo.reba.exception.BusinessException;
import ar.com.demo.reba.repository.RelacionPersonaRepository;
import ar.com.demo.reba.service.PersonaService;
import ar.com.demo.reba.service.RelacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RelacionServiceImpl implements RelacionService {

    private final RelacionPersonaRepository relacionPersonaRepository;
    private final PersonaService personaService;

    public RelacionServiceImpl(RelacionPersonaRepository relacionPersonaRepository, PersonaService personaService) {
        super();
        this.relacionPersonaRepository = relacionPersonaRepository;
        this.personaService = personaService;
    }


    @Override
    @Transactional
    public String getRelacion(String nroDoc1, Long idTipoDoc1, Long idPais1, String nroDoc2, Long idTipoDoc2, Long idPais2) throws BusinessException {
        if(this.isValid(nroDoc1,idTipoDoc1, idPais1) && this.isValid(nroDoc2, idTipoDoc2, idPais2)){
            Optional<PersonaEntity> persona1 = this.personaService.getPersonaById(nroDoc1, idTipoDoc1, idPais1);
            Optional<PersonaEntity> persona2 = this.personaService.getPersonaById(nroDoc2, idTipoDoc2, idPais2);
            Optional<PersonaEntity> padrePersona1 = this.getPadre(nroDoc1, idTipoDoc1, idPais1);
            Optional<PersonaEntity> padrePersona2 = this.getPadre(nroDoc2, idTipoDoc2, idPais2);
            Optional<PersonaEntity> padreSegundo1 = Optional.empty();
            Optional<PersonaEntity> padreSegundo2 = Optional.empty();

            if(padrePersona1.isPresent()){
                padreSegundo1 = this.getPadre(padrePersona1.get().getNroDocumento(),
                        padrePersona1.get().getTipoDocumento().getId(), padrePersona1.get().getPais().getId());
            }
            if(padrePersona2.isPresent()){
                padreSegundo2 = this.getPadre(padrePersona2.get().getNroDocumento(),
                        padrePersona2.get().getTipoDocumento().getId(), padrePersona2.get().getPais().getId());
            }
            if(persona1.isEmpty() || persona2.isEmpty()){
                throw new BusinessException(400, "Una de las personas ingresadas no se encuentra registrada.");
            }

            if(persona1.get().equals(persona2.get())){
                return "La persona es la misma.";
            }

            if(padrePersona1.isPresent() && padrePersona1.get().equals(persona2.get())){
                return "HIJ@";
            }

            if(padrePersona2.isPresent() && padrePersona2.get().equals(persona1.get())){
                    return "PADRE";
            }

            if(padrePersona1.isPresent() && padrePersona2.isPresent() && padrePersona1.get().equals(padrePersona2.get())){
                    return "HERMAN@";
            }else{
                if (padreSegundo1.isPresent() && padreSegundo2.isPresent() && padreSegundo1.get().equals(padreSegundo2.get())){
                    return "PRIM@";
                }
            }


            if(padreSegundo2.isPresent() && padrePersona1.isPresent() && padrePersona1.get().equals(padreSegundo2.get())){
                return "TI@";
            }

        } else {
            throw new BusinessException(400, "Los identificadores de personas no pueden ser nulos");
        }

        return "No hay registro de relación entre las personas.";
    }

    private boolean isValid(String nroDoc, Long idTipoDoc, Long idPais){
        return nroDoc != null && !nroDoc.trim().isEmpty() && idTipoDoc != null && idTipoDoc > 0L
                && idPais != null && idPais > 0L;
    }


    @Transactional(readOnly = true)
    public Optional<PersonaEntity> getPadre(String nroDocHijo, Long idTipoDocHijo, Long idPaisHijo){
        Optional<PersonaEntity> optHijo = this.personaService.getPersonaById(nroDocHijo, idTipoDocHijo, idPaisHijo);

        if(optHijo.isPresent()){
            RelacionPersonasEntity relacionPersonas = this.relacionPersonaRepository.findByPersonaHijo(optHijo.get());
            if(relacionPersonas != null && relacionPersonas.getPersonaPadre() != null){
                return Optional.of(relacionPersonas.getPersonaPadre());
            }else{
                return Optional.empty();
            }
        }else{
            return Optional.empty();
        }
    }


}
