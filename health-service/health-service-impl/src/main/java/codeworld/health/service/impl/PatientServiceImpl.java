/**
 *
 */
package codeworld.health.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codeworld.health.service.api.PatientService;
import codeworld.health.service.entity.HospitalEntity;
import codeworld.health.service.entity.PatientEntity;
import codeworld.health.service.model.PatientDto;
import codeworld.health.service.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author h.kanure
 *
 */
@Slf4j
@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    @Resource
    private PatientRepository repository;

    @Override
    public List<PatientDto> findAll() {
        List<PatientEntity> cityEntities = (List<PatientEntity>) repository.findAll();
        return cityEntities.stream()
                .map(data -> convertToDto(data))
                .collect(Collectors.toList());
    }

    public static PatientDto convertToDto(PatientEntity data) {
        return PatientDto.builder()
                .id(data.getId())
                .name(data.getName())
                .aadharNo(data.getAadharNo())
                .address(data.getAddress())
                .report(data.getReport())
                .admitDate(data.getAdmitDate())
                .releaseDate(data.getReleaseDate())
                .amount(data.getAmount())
                .hospitalDto(HospitalServiceImpl.convertToDto(data.getHospitalEntity()))
                .build();
    }

    public static PatientEntity convertToEntity(PatientDto data) {
        return PatientEntity.builder()
                .id(data.getId())
                .name(data.getName())
                .aadharNo(data.getAadharNo())
                .address(data.getAddress())
                .report(data.getReport())
                .admitDate(data.getAdmitDate())
                .releaseDate(data.getReleaseDate())
                .amount(data.getAmount())
                .hospitalEntity(HospitalEntity.builder()
                        .id(data.getHospitalDto()
                                .getId())
                        .build())
                .build();
    }

    @Override
    public Long create(PatientDto dto) {
        PatientEntity patientEntity = repository.save(convertToEntity(dto));
        log.info("Patient entity created with ID " + patientEntity.getId() + " With name " + patientEntity.getName());
        return patientEntity.getId();
    }

    @Override
    public Long update(PatientDto dto) {
        return repository.save(convertToEntity(dto))
                .getId();
    }

    @Override
    public PatientDto findById(@NotNull Long id) {
        Optional<PatientEntity> entityOptional = repository.findById(id);
        if (entityOptional.isPresent()) {
            log.info("Patient entity is fetching by ID " + id);
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
