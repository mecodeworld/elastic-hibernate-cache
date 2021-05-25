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
public class StaffDto implements Serializable {

    private static final long serialVersionUID = 5688352733738428209L;

    private Long id;
    private String name;
    private String jobType;
    private String address;
    private boolean deactive;

}
