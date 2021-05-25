package codeworld.health.service.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "COUNTRY")
public class CountryEntity implements Serializable {

    private static final long serialVersionUID = 9000312869569859245L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COUNTRY_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "REGION")
    private String region;

    @Column(name = "POPULATION")
    private Long population;

    @OneToMany(mappedBy = "countryEntity")
    private List<StateEntity> stateEntities;

}
