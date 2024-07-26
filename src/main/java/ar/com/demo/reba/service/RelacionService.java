package ar.com.demo.reba.service;

import ar.com.demo.reba.exception.BusinessException;

public interface RelacionService {

    String getRelacion(String nroDoc1, Long idTipoDoc1, Long idPais1, String nroDoc2, Long idTipoDoc2, Long idPais2) throws BusinessException;

}
