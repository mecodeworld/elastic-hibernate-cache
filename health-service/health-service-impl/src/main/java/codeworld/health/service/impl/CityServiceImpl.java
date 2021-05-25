package codeworld.health.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codeworld.health.service.api.CityService;
import codeworld.health.service.entity.CityEntity;
import codeworld.health.service.model.CityDto;
import codeworld.health.service.repository.CityRepository;

@Service
@Transactional
public class CityServiceImpl implements CityService {

    @Resource
    private CityRepository repository;

    public static CityDto convertToDto(CityEntity data) {
        return CityDto.builder()
                .id(data.getId())
                .name(data.getName())
                .population(data.getPopulation())
                .stateDto(StateServiceImpl.convertToDto(data.getStateEntity()))
                .build();
    }

    public static CityEntity convertToEntity(CityDto data) {
        return CityEntity.builder()
                .id(data.getId())
                .name(data.getName())
                .population(data.getPopulation())
                .stateEntity(StateServiceImpl.convertToEntity(data.getStateDto()))
                .build();
    }

    @Override
    public List<CityDto> findAll() {
        List<CityEntity> cityEntities = (List<CityEntity>) repository.findAll();
        return cityEntities.stream()
                .map(data -> convertToDto(data))
                .collect(Collectors.toList());
    }

    @Override
    public Long create(CityDto dto) {
        return repository.save(convertToEntity(dto))
                .getId();
    }

    @Override
    public Long update(CityDto dto) {
        return repository.save(convertToEntity(dto))
                .getId();
    }

    @Override
    public CityDto findById(@NotNull Long id) {
        Optional<CityEntity> entityOptional = repository.findById(id);
        if (entityOptional.isPresent()) {
            return convertToDto(entityOptional.get());
        }
        else {
            throw new BadRequestException("Data not found");
        }
    }

    @Override
    public void delete(@NotNull Long id) {
        repository.deleteById(id);
    }

}
