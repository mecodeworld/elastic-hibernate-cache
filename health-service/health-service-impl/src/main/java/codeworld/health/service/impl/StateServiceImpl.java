package codeworld.health.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codeworld.health.service.api.StateService;
import codeworld.health.service.entity.StateEntity;
import codeworld.health.service.model.StateDto;
import codeworld.health.service.repository.StateRepository;

@Service
@Transactional
public class StateServiceImpl implements StateService {

    @Resource
    private StateRepository repository;

    @Override
    public List<StateDto> findAll() {
        List<StateEntity> stateEntities = (List<StateEntity>) repository.findAll();
        return stateEntities.stream()
                .map(data -> convertToDto(data))
                .collect(Collectors.toList());
    }

    public static StateDto convertToDto(StateEntity data) {
        return StateDto.builder()
                .id(data.getId())
                .name(data.getName())
                .population(data.getPopulation())
                .countryDto(CountryServiceImpl.convertToDto(data.getCountryEntity()))
                .build();
    }

    public static StateEntity convertToEntity(StateDto data) {
        return StateEntity.builder()
                .id(data.getId())
                .name(data.getName())
                .population(data.getPopulation())
                .countryEntity(CountryServiceImpl.convertToEntity(data.getCountryDto()))
                .build();
    }

    @Override
    public Long create(StateDto dto) {
        return repository.save(convertToEntity(dto))
                .getId();
    }

    @Override
    public Long update(StateDto dto) {
        return repository.save(convertToEntity(dto))
                .getId();
    }

    @Override
    public void delete(@NotNull Long id) {
        repository.deleteById(id);
    }

    @Override
    public StateDto findById(@NotNull Long id) {
        Optional<StateEntity> entityOptional = repository.findById(id);
        if (entityOptional.isPresent()) {
            return convertToDto(entityOptional.get());
        }
        else {
            throw new BadRequestException("Data not found");
        }
    }

}
