package codeworld.health.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import codeworld.health.service.entity.BillEntity;

/**
 * @author h.kanure
 *
 */
@Repository
public interface BillRepository extends CrudRepository<BillEntity, Long> {

}
