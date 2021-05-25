package codeworld.health.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import codeworld.health.service.entity.StaffEntity;

@Repository
public interface StaffRepository extends CrudRepository<StaffEntity, Long> {

}
