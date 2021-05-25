package codeworld.health.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codeworld.health.service.api.DepartmentService;
import codeworld.health.service.entity.DepartmentEntity;
import codeworld.health.service.model.DepartmentDto;
import codeworld.health.service.repository.DepartmentRepository;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentRepository repository;

    public static DepartmentDto convertToDto(DepartmentEntity data) {
        return DepartmentDto.builder()
                .id(data.getId())
                .name(data.getName())
                .detail(data.getDetail())
                .deactive(data.isDeactive())
                .build();
    }

    public static DepartmentEntity convertToEntity(DepartmentDto data) {
        return DepartmentEntity.builder()
                .id(data.getId())
                .name(data.getName())
                .detail(data.getDetail())
                .deactive(data.isDeactive())
                .build();
    }

    @Override
    public List<DepartmentDto> findAll() {
        List<DepartmentEntity> entities = (List<DepartmentEntity>) repository.findAll();
        return entities.stream()
                .map(data -> convertToDto(data))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto findById(@NotNull Long id) {
        Optional<DepartmentEntity> entityOptional = repository.findById(id);
        if (entityOptional.isPresent()) {
            return convertToDto(entityOptional.get());
        }
        else {
            throw new BadRequestException("Data not found");
        }
    }

    @Override
    public Long create(DepartmentDto dto) {
        return repository.save(convertToEntity(dto))
                .getId();
    }

    @Override
    public Long update(DepartmentDto dto) {
        return repository.save(convertToEntity(dto))
                .getId();
    }

    @Override
    public void delete(@NotNull Long id) {
        repository.deleteById(id);
    }

}
