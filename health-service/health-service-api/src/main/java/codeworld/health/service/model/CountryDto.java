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
public class CountryDto implements Serializable {

    private static final long serialVersionUID = 9000312869569859245L;

    private Long id;
    private String name;
    private String region;
    private Long population;
    private List<StateDto> stateDtos;

}
