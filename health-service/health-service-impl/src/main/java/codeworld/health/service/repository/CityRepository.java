package codeworld.health.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import codeworld.health.service.entity.CityEntity;

/**
 * @author h.kanure
 *
 */
@Repository
public interface CityRepository extends CrudRepository<CityEntity, Long> {

}
