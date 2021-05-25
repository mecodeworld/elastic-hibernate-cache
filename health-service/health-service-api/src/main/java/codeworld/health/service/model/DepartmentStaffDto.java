package codeworld.health.service.model;

import java.io.Serializable;

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
public class DepartmentStaffDto implements Serializable {

    private static final long serialVersionUID = -7407044532419519927L;
    private Long id;
    private HospitalDepartmentDto hospitalDepartmentDto;
    private StaffDto staffDto;

}
