package home.app.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import home.app.api.common.model.NamedEntity;
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
public class User extends NamedEntity {

    private static final long serialVersionUID = 34350280618224558L;

    private String email;
    private String password;
    private String username;

}
