package ar.com.demo.reba.repository;

import ar.com.demo.reba.entity.PaisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends JpaRepository<PaisEntity, Long> {
}
