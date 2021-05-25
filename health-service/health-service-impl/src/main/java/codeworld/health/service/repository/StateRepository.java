package codeworld.health.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import codeworld.health.service.entity.StateEntity;

/**
 * @author h.kanure
 *
 */
@Repository
public interface StateRepository extends CrudRepository<StateEntity, Long> {

}
