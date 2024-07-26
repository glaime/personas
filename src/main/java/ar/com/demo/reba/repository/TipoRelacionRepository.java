package ar.com.demo.reba.repository;

import ar.com.demo.reba.entity.TipoRelacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRelacionRepository extends JpaRepository<TipoRelacionEntity, Long> {

}
