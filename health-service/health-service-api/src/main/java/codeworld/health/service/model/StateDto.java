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
public class StateDto implements Serializable {

    private static final long serialVersionUID = 3494402655250133510L;

    private Long id;
    private String name;
    private Long population;
    private CountryDto countryDto;
    private List<CityDto> cityDtos;

}
