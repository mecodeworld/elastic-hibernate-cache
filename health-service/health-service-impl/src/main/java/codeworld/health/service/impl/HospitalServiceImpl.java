package codeworld.health.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codeworld.health.service.api.HospitalService;
import codeworld.health.service.entity.HospitalDepartmentEntity;
import codeworld.health.service.entity.HospitalEntity;
import codeworld.health.service.model.HospitalDto;
import codeworld.health.service.repository.HospitalRepository;

@Service
@Transactional
public class HospitalServiceImpl implements HospitalService {

    @Resource
    private HospitalRepository repository;

    public static HospitalDto convertToDto(HospitalEntity data) {
        return HospitalDto.builder()
                .id(data.getId())
                .name(data.getName())
                .licenseNo(data.getLicenseNo())
                .type(data.getType())
                .cityDto(CityServiceImpl.convertToDto(data.getCityEntity()))
                .hospitalDepartmentDtos(data.getHospitalDepartmentEntities()
                        .stream()
                        .map(f -> HospitalDepartmentEntity.convertToDto(f))
                        .collect(Collectors.toList()))
                .build();
    }

    public static HospitalEntity convertToEntity(HospitalDto data) {

        HospitalEntity entity = HospitalEntity.builder()
                .id(data.getId())
                .name(data.getName())
                .licenseNo(data.getLicenseNo())
                .type(data.getType())
                .cityEntity(CityServiceImpl.convertToEntity(data.getCityDto()))
                .build();

        List<HospitalDepartmentEntity> hospitalDepartmentEntities = data.getHospitalDepartmentDtos()
                .stream()
                .map(f -> HospitalDepartmentEntity.convertToEntity(f, entity))
                .collect(Collectors.toList());

        entity.setHospitalDepartmentEntities(hospitalDepartmentEntities);

        return entity;
    }

    @Override
    public List<HospitalDto> findAll() {
        List<HospitalEntity> cityEntities = (List<HospitalEntity>) repository.findAll();
        return cityEntities.stream()
                .map(data -> convertToDto(data))
                .collect(Collectors.toList());
    }

    @Override
    public Long create(HospitalDto dto) {
        return repository.save(convertToEntity(dto))
                .getId();
    }

    @Override
    public Long update(HospitalDto dto) {
        return repository.save(convertToEntity(dto))
                .getId();
    }

    @Override
    public HospitalDto findById(@NotNull Long id) {
        Optional<HospitalEntity> entityOptional = repository.findById(id);
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
