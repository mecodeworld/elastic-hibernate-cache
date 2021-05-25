package codeworld.health.service.entity;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import codeworld.health.service.model.DepartmentDto;
import codeworld.health.service.model.HospitalDepartmentDto;
import codeworld.health.service.model.HospitalDto;
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
@Table(name = "HOSPITAL_DEPT")
public class HospitalDepartmentEntity implements Serializable {

    private static final long serialVersionUID = -4903677482331604893L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HOSPITAL_DEPT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOSPITAL_ID")
    private HospitalEntity hospitalEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_ID")
    private DepartmentEntity departmentEntity;

    @OneToMany(orphanRemoval = true,
            cascade = CascadeType.ALL,
            mappedBy = "hospitalDepartmentEntity")
    private List<DepartmentStaffEntity> departmentStaffEntities;

    public static HospitalDepartmentEntity convertToEntity(HospitalDepartmentDto data, HospitalEntity entity) {

        HospitalDepartmentEntity hospitalDepartmentEntity = HospitalDepartmentEntity.builder()
                .id(data.getId())
                .departmentEntity(DepartmentEntity.builder()
                        .id(data.getDepartmentDto()
                                .getId())
                        .build())
                .hospitalEntity(entity)
                .build();

        List<DepartmentStaffEntity> departmentStaffEntities = data.getDepartmentStaffDtos()
                .stream()
                .map(f -> DepartmentStaffEntity.convertToEntity(f, hospitalDepartmentEntity))
                .collect(Collectors.toList());
        hospitalDepartmentEntity.setDepartmentStaffEntities(departmentStaffEntities);

        return hospitalDepartmentEntity;
    }

    public static HospitalDepartmentDto convertToDto(HospitalDepartmentEntity data) {
        return HospitalDepartmentDto.builder()
                .id(data.getId())
                .departmentDto(DepartmentDto.builder()
                        .id(data.getDepartmentEntity()
                                .getId())
                        .name(data.getDepartmentEntity()
                                .getName())
                        .build())
                .hospitalDto(HospitalDto.builder()
                        .id(data.getHospitalEntity()
                                .getId())
                        .name(data.getHospitalEntity()
                                .getName())
                        .build())
                .build();
    }

}
