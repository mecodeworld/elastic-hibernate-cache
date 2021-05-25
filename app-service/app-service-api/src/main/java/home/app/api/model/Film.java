package home.app.api.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import home.app.api.common.model.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Film extends IdEntity {

    private static final long serialVersionUID = 2354681105715462738L;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private String rating;
    private String features;
    private String language;

}
