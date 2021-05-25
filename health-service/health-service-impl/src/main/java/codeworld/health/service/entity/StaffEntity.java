package codeworld.health.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Entity
@Table(name = "STAFF")
public class StaffEntity implements Serializable {

    private static final long serialVersionUID = 5688352733738428209L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STAFF_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "JOB_TYPE")
    private String jobType;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "IS_DEACTIVE")
    private boolean deactive;

}
