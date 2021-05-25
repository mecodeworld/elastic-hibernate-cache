package codeworld.health.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import codeworld.health.service.entity.PatientEntity;

/**
 * @author h.kanure
 *
 */
@Repository
public interface PatientRepository extends CrudRepository<PatientEntity, Long> {

}
