package ar.com.demo.reba.service.impl;

import ar.com.demo.reba.dto.request.PersonaRequestDTO;
import ar.com.demo.reba.dto.request.RelacionPersonasRequestDTO;
import ar.com.demo.reba.dto.response.PersonaResponseDTO;
import ar.com.demo.reba.dto.response.RelacionPersonasResponseDTO;
import ar.com.demo.reba.entity.*;
import ar.com.demo.reba.exception.BusinessException;
import ar.com.demo.reba.repository.*;
import ar.com.demo.reba.service.PersonaService;
import ar.com.demo.reba.service.mapper.PersonaMapper;
import ar.com.demo.reba.service.mapper.RelacionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PersonaServiceImpl implements PersonaService {

    private final PersonaRepository repository;
    private final PaisRepository paisRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final TipoRelacionRepository tipoRelacionRepository;
    private final RelacionPersonaRepository relacionPersonaRepository;

    public PersonaServiceImpl(PersonaRepository repository, PaisRepository paisRepository, TipoDocumentoRepository tipoDocumentoRepository,
                              TipoRelacionRepository tipoRelacionRepository, RelacionPersonaRepository relacionPersonaRepository) {
        super();
        this.repository = repository;
        this.paisRepository = paisRepository;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
        this.tipoRelacionRepository = tipoRelacionRepository;
        this.relacionPersonaRepository = relacionPersonaRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<PersonaResponseDTO> getAll() {
        return PersonaMapper.toDtoList(this.repository.findAll());
    }

    @Override
    public PersonaResponseDTO getById(String nroDoc, Long idTipoDoc, Long idPais) {
        PersonaResponseDTO response = new PersonaResponseDTO();
        Optional<PersonaEntity> optPersona = this.getPersonaById(nroDoc, idTipoDoc, idPais);
        if(optPersona.isPresent()){
            response = PersonaMapper.toDto(optPersona.get());
        }
        return response;
    }

    @Override
    @Transactional
    public PersonaResponseDTO create(PersonaRequestDTO body) throws BusinessException {
        try{
            return this.validAndSaveAndBuildResponseDTO(body, "create");
        } catch (BusinessException ex) {
            throw new BusinessException(ex.getCode(), ex.getMessage());
        }
    }

    private void validarPersona(PersonaRequestDTO request, String action) throws BusinessException {
        Optional<PersonaEntity> persona = this.getPersonaById(request.getNroDocumento(), request.getIdTipoDoc(), request.getIdPais());
        if(persona.isPresent() && action.equalsIgnoreCase("create")){
            throw new BusinessException(1001 , "Ya existe una persona con los datos ingresados.");
        }else {
            if( (request.getNroCel() != null && !request.getNroCel().trim().isEmpty())
                    || (request.getNroAlt() != null && !request.getNroAlt().isEmpty()) ){
                if(this.calcularEdad(request.getFechaNacimiento()) < 18){
                    throw new BusinessException(1003 , "El registro solo es permitido para mayores de 18 años.");
                }
            }else{
                throw new BusinessException(1002 , "Debe completar al menos un número de teléfono.");
            }

        }
    }


    private IdPersonaEntity validarPaisyTipoDoc(IdPersonaEntity idPersona) throws BusinessException {
        Optional<PaisEntity> optPais = this.paisRepository.findById(idPersona.getPais().getId());
        if(optPais.isPresent()){
            idPersona.setPais(optPais.get());
        }else{
            throw new BusinessException(404, "El país ingresado no existe.");
        }
        Optional<TipoDocumentoEntity> optTipoDocumento = this.tipoDocumentoRepository.findById(idPersona.getTipoDocumento().getId());
        if(optTipoDocumento.isPresent()){
            idPersona.setTipoDocumento(optTipoDocumento.get());
        }else{
            throw new BusinessException(404, "El Tipo Documento ingresado no existe.");
        }
        return idPersona;
    }

    private int calcularEdad(Date fechaNacimiento){
        LocalDate fechaNacimientoLocalDate = fechaNacimiento.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate today = LocalDate.now();
        return Period.between(fechaNacimientoLocalDate, today).getYears();
    }


    @Override
    @Transactional
    public PersonaResponseDTO update(PersonaRequestDTO body) throws BusinessException {
        try{
            PersonaResponseDTO response;
            Optional<PersonaEntity> optionalPersona = this.getPersonaById(body.getNroDocumento(), body.getIdTipoDoc(), body.getIdPais());
            if(optionalPersona.isPresent()){
                response = this.validAndSaveAndBuildResponseDTO(body, "update");
            }else {
                throw new BusinessException(404, "No existe persona con los datos ingresados.");
            }
            return response;
        } catch (BusinessException ex){
            throw new BusinessException(ex.getCode(), ex.getMessage());
        }
    }

    private PersonaResponseDTO validAndSaveAndBuildResponseDTO(PersonaRequestDTO body, String accion) throws BusinessException {
        this.validarPersona(body, accion);
        IdPersonaEntity idPersona = PersonaMapper.buildIdPersonaEntity(body.getNroDocumento(), body.getIdTipoDoc(), body.getIdPais());
        this.validarPaisyTipoDoc(idPersona);
        PersonaEntity persona = this.repository.save(PersonaMapper.toEntity(body, idPersona));
        return PersonaMapper.toDto(persona);
    }

    @Override
    @Transactional
    public void deleteById(String nroDoc, Long idTipoDoc, Long idPais) throws BusinessException {
        if(nroDoc != null && !nroDoc.trim().isEmpty() && idTipoDoc != null && idTipoDoc > 0L && idPais != null && idPais > 0L){
            Optional<PersonaEntity> optPersona = this.getPersonaById(nroDoc, idTipoDoc, idPais);
            if(optPersona.isPresent()){
                IdPersonaEntity idPersona = PersonaMapper.buildIdPersonaEntity(nroDoc, idTipoDoc, idPais);
                this.repository.deleteById(idPersona);
            }else{
                throw new BusinessException(404, "No se encontró registro de personas con los datos ingresados.");
            }
        }
    }

    @Override
    @Transactional
    public RelacionPersonasResponseDTO createRelacionPersonas(String nroDocPadre, String nroDocHijo, RelacionPersonasRequestDTO body) throws BusinessException {
        RelacionPersonasEntity relacionPersonas = new RelacionPersonasEntity();
        Optional<PersonaEntity> optPadre = this.getPersonaById(nroDocPadre, body.getIdTipoDocPadre(), body.getIdPaisPadre());
        if(optPadre.isPresent()){
            relacionPersonas.setPersonaPadre(optPadre.get());
        }else{
            throw new BusinessException(404, "No se encontró registros de la persona declarada como padre.");
        }
        Optional<PersonaEntity> optHijo = this.getPersonaById(nroDocHijo, body.getIdTipoDocHijo(), body.getIdPaisHijo());
        if(optHijo.isPresent()){
            relacionPersonas.setPersonaHijo(optHijo.get());
        }else{
            throw new BusinessException(404, "No se encontró registros de la persona declarada como hij@.");
        }
        //ID 1 = PADRE
        Optional<TipoRelacionEntity> optTipoRelacion = this.tipoRelacionRepository.findById(1L);
        if(optTipoRelacion.isPresent()){
            relacionPersonas.setRelacion(optTipoRelacion.get());
        }else {
            throw new BusinessException(404, "No se encontró el tipo de relación.");
        }

        return RelacionMapper.toDto(this.relacionPersonaRepository.save(relacionPersonas));

    }


    @Override
    public Optional<PersonaEntity> getPersonaById(String nroDoc, Long idTipoDoc, Long idPais){

        IdPersonaEntity idPersona = PersonaMapper.buildIdPersonaEntity(nroDoc, idTipoDoc, idPais);

        Optional<PersonaEntity> optPersona = this.repository.findById(idPersona);
        if(optPersona.isPresent()){
            Optional<PaisEntity> pais = this.paisRepository.findById(idPais);
            if(pais.isPresent()){
                idPersona.setPais(pais.get());
            }
            Optional<TipoDocumentoEntity> tipoDocumento = this.tipoDocumentoRepository.findById(idTipoDoc);
            if(tipoDocumento.isPresent()){
                idPersona.setTipoDocumento(tipoDocumento.get());
            }
            optPersona.get().setNroDocumento(optPersona.get().getNroDocumento());
            optPersona.get().setTipoDocumento(idPersona.getTipoDocumento());
            optPersona.get().setPais(idPersona.getPais());
            return optPersona;
        }else {
            return Optional.empty();
        }
    }
}
