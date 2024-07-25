package ar.com.demo.reba.service.impl;

import ar.com.demo.reba.entity.PersonaEntity;
import ar.com.demo.reba.entity.RelacionPersonasEntity;
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
    public String getRelacion(String nroDoc1, Long idTipoDoc1, Long idPais1, String nroDoc2, Long idTipoDoc2, Long idPais2) {

        Optional<PersonaEntity> padrePersona1 = this.getPadre(nroDoc1, idTipoDoc1, idPais1);
        Optional<PersonaEntity> padrePersona2 = this.getPadre(nroDoc2, idTipoDoc2, idPais2);

        if(padrePersona1.get().getNroDocumento() != null && !padrePersona1.get().getNroDocumento().trim().isEmpty() &&
                padrePersona1.get().getTipoDocumento().getId() != null && padrePersona1.get().getPais().getId() != null &&
                padrePersona2.get().getNroDocumento() != null && !padrePersona2.get().getNroDocumento().trim().isEmpty() &&
                padrePersona2.get().getTipoDocumento().getId() != null && padrePersona2.get().getPais().getId() != null){
            if(padrePersona1.get().equals(padrePersona2.get())){
                return "HERMAN@";
            }
        }

        return null;
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
