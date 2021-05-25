package home.app.service.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue; 
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import home.app.service.entity.FilmEntity.FilmEntityBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LANGUAGE")
public class LanguageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LANGUAGE_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LAST_UPDATE")
    private LocalDateTime lastUpdate;

}
