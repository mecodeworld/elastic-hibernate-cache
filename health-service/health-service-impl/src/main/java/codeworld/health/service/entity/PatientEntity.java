package codeworld.health.service.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "PATIENT")
public class PatientEntity implements Serializable {

    private static final long serialVersionUID = -3672667237122201355L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PATIENT_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AADHAR_NO")
    private Long aadharNo;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "REPORT")
    private String report;

    @Column(name = "ADMIT_DATE")
    private Date admitDate;

    @Column(name = "RELEASE_DATE")
    private Date releaseDate;

    @Column(name = "AMOUNT")
    private Long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOSPITAL_ID")
    private HospitalEntity hospitalEntity;

}
