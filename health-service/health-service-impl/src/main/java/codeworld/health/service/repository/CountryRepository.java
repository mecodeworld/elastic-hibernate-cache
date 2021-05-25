package codeworld.health.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import codeworld.health.service.entity.CountryEntity;

/**
 * @author h.kanure
 *
 */
@Repository
public interface CountryRepository extends CrudRepository<CountryEntity, Long> {

}
