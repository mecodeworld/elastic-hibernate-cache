package home.app.service.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "FILM")
public class FilmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FILM_ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "RELEASE_YEAR")
    private LocalDate releaseYear;

    @Column(name = "RATING")
    private String rating;

    @Column(name = "SPECIAL_FEATURES")
    private String features;

}
