package codeworld.health.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codeworld.health.service.api.StaffService;
import codeworld.health.service.entity.StaffEntity;
import codeworld.health.service.model.StaffDto;
import codeworld.health.service.repository.StaffRepository;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {

    @Resource
    private StaffRepository repository;

    public static StaffDto convertToDto(StaffEntity data) {
        return StaffDto.builder()
                .id(data.getId())
                .name(data.getName())
                .jobType(data.getJobType())
                .address(data.getAddress())
                .deactive(data.isDeactive())
                .build();
    }

    public static StaffEntity convertToEntity(StaffDto data) {
        return StaffEntity.builder()
                .id(data.getId())
                .name(data.getName())
                .jobType(data.getJobType())
                .address(data.getAddress())
                .deactive(data.isDeactive())
                .build();
    }

    @Override
    public List<StaffDto> findAll() {
        List<StaffEntity> cityEntities = (List<StaffEntity>) repository.findAll();
        return cityEntities.stream()
                .map(data -> convertToDto(data))
                .collect(Collectors.toList());
    }

    @Override
    public Long create(StaffDto dto) {
        return repository.save(convertToEntity(dto))
                .getId();
    }

    @Override
    public Long update(StaffDto dto) {
        return repository.save(convertToEntity(dto))
                .getId();
    }

    @Override
    public StaffDto findById(@NotNull Long id) {
        Optional<StaffEntity> entityOptional = repository.findById(id);
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
