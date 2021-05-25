package codeworld.health.service.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "STATE")
public class StateEntity implements Serializable {

    private static final long serialVersionUID = 3494402655250133510L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATE_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "POPULATION")
    private Long population;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_ID")
    private CountryEntity countryEntity;

    @OneToMany(mappedBy = "stateEntity")
    private List<CityEntity> cityEntities;

}
