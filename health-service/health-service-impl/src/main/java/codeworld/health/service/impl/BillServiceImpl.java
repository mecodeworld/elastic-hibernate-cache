package codeworld.health.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codeworld.health.service.api.BillService;
import codeworld.health.service.entity.BillEntity;
import codeworld.health.service.model.BillDto;
import codeworld.health.service.repository.BillRepository;

@Service
@Transactional
public class BillServiceImpl implements BillService {

    @Resource
    private BillRepository repository;

    public static BillDto convertToDto(BillEntity data) {
        return BillDto.builder()
                .id(data.getId())
                .name(data.getName())
                .amount(data.getAmount())
                .hospitalDto(HospitalServiceImpl.convertToDto(data.getHospitalEntity()))
                .patientDto(PatientServiceImpl.convertToDto(data.getPatientEntity()))
                .build();
    }

    public static BillEntity convertToEntity(BillDto data) {
        return BillEntity.builder()
                .id(data.getId())
                .name(data.getName())
                .amount(data.getAmount())
                .hospitalEntity(HospitalServiceImpl.convertToEntity(data.getHospitalDto()))
                .patientEntity(PatientServiceImpl.convertToEntity(data.getPatientDto()))
                .build();
    }

    @Override
    public List<BillDto> findAll() {
        List<BillEntity> cityEntities = (List<BillEntity>) repository.findAll();
        return cityEntities.stream()
                .map(data -> convertToDto(data))
                .collect(Collectors.toList());
    }

    @Override
    public Long create(BillDto dto) {
        BillEntity entity = repository.save(convertToEntity(dto));
        return entity.getId();
    }

    @Override
    public Long update(BillDto dto) {
        BillEntity entity = repository.save(convertToEntity(dto));
        return entity.getId();
    }

    @Override
    public BillDto findById(@NotNull Long id) {
        Optional<BillEntity> entityOptional = repository.findById(id);
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
