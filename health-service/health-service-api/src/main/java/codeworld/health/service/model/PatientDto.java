package codeworld.health.service.model;

import java.io.Serializable;
import java.util.Date;

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
public class PatientDto implements Serializable {

    private static final long serialVersionUID = -3672667237122201355L;

    private Long id;
    private String name;
    private Long aadharNo;
    private String address;
    private String report;
    private Date admitDate;
    private Date releaseDate;
    private Long amount;
    private HospitalDto hospitalDto;

}
