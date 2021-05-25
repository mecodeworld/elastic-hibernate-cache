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
public class HospitalDepartmentDto implements Serializable {

    private static final long serialVersionUID = 7579746049835156927L;

    private Long id;
    private HospitalDto hospitalDto;
    private DepartmentDto departmentDto;
    private List<DepartmentStaffDto> departmentStaffDtos;

}
