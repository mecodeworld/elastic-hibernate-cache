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
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto implements Serializable {

    private static final long serialVersionUID = -6285859682336616594L;

    private Long id;
    private String name;
    private String detail;
    private boolean deactive;
}
