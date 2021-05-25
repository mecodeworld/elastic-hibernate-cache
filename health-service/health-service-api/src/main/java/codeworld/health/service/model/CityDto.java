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
public class CityDto implements Serializable {

    private static final long serialVersionUID = 4604671787376676658L;

    private Long id;
    private String name;
    private Long population;
    private StateDto stateDto;
    private List<HospitalDto> hospitalDtos;

}
