package home.app.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import home.app.service.entity.FilmEntity;

/**
 * @author h.kanure
 *
 */
@Repository
public interface FilmRepository extends CrudRepository<FilmEntity, Long> {

}
