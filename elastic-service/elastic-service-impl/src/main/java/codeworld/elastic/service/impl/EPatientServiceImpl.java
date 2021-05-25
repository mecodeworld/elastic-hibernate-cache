package codeworld.elastic.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codeworld.elastic.service.api.EPatientService;
import codeworld.elastic.service.document.PatientDoc;
import codeworld.elastic.service.repository.PatientRepository;
import codeworld.health.service.model.HospitalDto;
import codeworld.health.service.model.PatientDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class EPatientServiceImpl implements EPatientService {

    @Resource
    private PatientRepository patientRepository;

    public static PatientDoc convertToDocument(PatientDto patientDto) {
        return PatientDoc.builder()
                .id(patientDto.getId())
                .name(patientDto.getName())
                .aadharNo(patientDto.getAadharNo())
                .address(patientDto.getAddress())
                .admitDate(patientDto.getAdmitDate())
                .amount(patientDto.getAmount())
                .releaseDate(patientDto.getReleaseDate())
                .report(patientDto.getReport())
                .hospitalName(patientDto.getHospitalDto()
                        .getName())
                .build();
    }

    public static PatientDto convertToDto(PatientDoc patient) {
        return PatientDto.builder()
                .id(patient.getId())
                .name(patient.getName())
                .aadharNo(patient.getAadharNo())
                .address(patient.getAddress())
                .admitDate(patient.getAdmitDate())
                .amount(patient.getAmount())
                .releaseDate(patient.getReleaseDate())
                .report(patient.getReport())
                .hospitalDto(HospitalDto.builder()
                        .name(patient.getHospitalName())
                        .build())
                .build();
    }

    @Override
    public List<PatientDto> findAll() {
        List<PatientDto> resultList = new ArrayList<PatientDto>();
        Iterable<PatientDoc> result = patientRepository.findAll();
        result.forEach(data -> {
            resultList.add(convertToDto(data));
        });
        return resultList;
    }

    @Override
    public Optional<PatientDto> findById(@NotNull Long id) {
        Optional<PatientDoc> patientDoc = patientRepository.findById(id);
        if (patientDoc.isPresent()) {
            log.info("Patient Document is fetching by ID " + id);
            return Optional.ofNullable(convertToDto(patientDoc.get()));
        }
        return Optional.empty();
    }

    @Override
    public void delete(@NotNull Long id) {
        Optional<PatientDoc> patientDoc = patientRepository.findById(id);
        if (patientDoc.isPresent()) {
            patientRepository.delete(patientDoc.get());
        }
    }

}
