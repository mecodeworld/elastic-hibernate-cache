package home.app.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import home.app.api.common.model.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Actor extends IdEntity {

    private static final long serialVersionUID = -2377081323418936665L;
    private String fname;
    private String lname;

}
