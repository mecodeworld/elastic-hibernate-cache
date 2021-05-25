package codeworld.health.service.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDto implements Serializable {

    private static final long serialVersionUID = -6212591030116363521L;

    private Long id;
    private String name;
    private String licenseNo;
    private String type;
    private CityDto cityDto;
    private List<PatientDto> patientDtos;
    private List<HospitalDepartmentDto> hospitalDepartmentDtos;
}
