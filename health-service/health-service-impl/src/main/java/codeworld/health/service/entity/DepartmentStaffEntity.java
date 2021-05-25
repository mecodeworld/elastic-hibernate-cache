package codeworld.health.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import codeworld.health.service.impl.DepartmentServiceImpl;
import codeworld.health.service.impl.HospitalServiceImpl;
import codeworld.health.service.model.DepartmentStaffDto;
import codeworld.health.service.model.HospitalDepartmentDto;
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
@Entity
@Table(name = "DEPT_STAFF")
public class DepartmentStaffEntity implements Serializable {

    private static final long serialVersionUID = -2949970875212306986L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPT_STAFF_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOSPITAL_DEPT_ID")
    private HospitalDepartmentEntity hospitalDepartmentEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STAFF_ID")
    private StaffEntity staffEntity;

    public static DepartmentStaffEntity convertToEntity(DepartmentStaffDto data, HospitalDepartmentEntity hospitalDepartmentEntity) {
        return DepartmentStaffEntity.builder()
                .id(data.getId())
                .hospitalDepartmentEntity(hospitalDepartmentEntity)
                .staffEntity(StaffEntity.builder()
                        .id(data.getStaffDto()
                                .getId())
                        .build())
                .build();
    }

    public static DepartmentStaffDto convertToDto(DepartmentStaffEntity data) {
        return DepartmentStaffDto.builder()
                .id(data.getId())
                .hospitalDepartmentDto(HospitalDepartmentDto.builder()
                        .hospitalDto(HospitalServiceImpl.convertToDto(data.getHospitalDepartmentEntity()
                                .getHospitalEntity()))
                        .departmentDto(DepartmentServiceImpl.convertToDto(data.getHospitalDepartmentEntity()
                                .getDepartmentEntity()))
                        .build())
                .staffDto(StaffDto.builder()
                        .id(data.getStaffEntity()
                                .getId())
                        .name(data.getStaffEntity()
                                .getName())
                        .build())
                .build();
    }

}
