/**
 *
 */
package home.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import home.app.api.model.Film;
import home.app.api.service.FilmService;
import home.app.service.entity.FilmEntity;
import home.app.service.repository.FilmRepository;

/**
 * @author h.kanure
 *
 */
@Service
@Transactional
public class FilmServiceImpl implements FilmService {

    @Resource
    private FilmRepository filmRepository;

    @Override
    public List<Film> findAll() {
        List<FilmEntity> entities = (List<FilmEntity>) filmRepository.findAll();
        return entities.stream()
                .map(this::converToDto)
                .collect(Collectors.toList());
    }

    private Film converToDto(FilmEntity entity) {
        return Film.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .features(entity.getFeatures())
                .rating(entity.getRating())
                .title(entity.getTitle())
                .releaseDate(entity.getReleaseYear())
                .build();
    }

}
