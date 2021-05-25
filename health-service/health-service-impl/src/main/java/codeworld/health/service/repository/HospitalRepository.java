package codeworld.health.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import codeworld.health.service.entity.HospitalEntity;

/**
 * @author h.kanure
 *
 */
@Repository
public interface HospitalRepository extends CrudRepository<HospitalEntity, Long> {

}
