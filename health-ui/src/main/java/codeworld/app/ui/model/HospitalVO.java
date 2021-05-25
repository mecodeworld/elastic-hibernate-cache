package codeworld.app.ui.model;

import java.io.Serializable;
import java.util.List;

import codeworld.health.service.model.CityDto;
import codeworld.health.service.model.DepartmentDto;
import codeworld.health.service.model.StaffDto;
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
public class HospitalVO implements Serializable {

    private static final long serialVersionUID = 8430782867213218475L;

    private Long id;
    private String name;
    private String licenseNo;
    private String type;
    private CityDto cityDto;
    private List<DepartmentDto> departmentDtos;
    private List<StaffDto> staffDtos;

}
