package codeworld.health.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import codeworld.health.service.entity.DepartmentEntity;

/**
 * @author h.kanure
 *
 */
@Repository
public interface DepartmentRepository extends CrudRepository<DepartmentEntity, Long> {

}
