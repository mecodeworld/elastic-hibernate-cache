package codeworld.health.service.entity;

import java.io.Serializable;
import java.util.List;

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
@Table(name = "HOSPITAL")
public class HospitalEntity implements Serializable {

    private static final long serialVersionUID = -6212591030116363521L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HOSPITAL_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LICENSE_NO")
    private String licenseNo;

    @Column(name = "TYPE")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CITY_ID")
    private CityEntity cityEntity;

    @OneToMany(mappedBy = "hospitalEntity")
    private List<PatientEntity> patientEntities;

    @OneToMany(orphanRemoval = true,
            cascade = CascadeType.ALL,
            mappedBy = "hospitalEntity")
    private List<HospitalDepartmentEntity> hospitalDepartmentEntities;

}
