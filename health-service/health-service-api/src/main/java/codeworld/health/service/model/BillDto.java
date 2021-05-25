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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class BillDto implements Serializable {

    private static final long serialVersionUID = 7292874422316301900L;

    private Long id;
    private String name;
    private Long amount;
    private PatientDto patientDto;
    private HospitalDto hospitalDto;

}
