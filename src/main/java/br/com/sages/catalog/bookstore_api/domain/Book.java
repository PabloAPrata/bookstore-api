package br.com.sages.catalog.bookstore_api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "author", nullable = false, length = 256)
    private String author;

    @Column(name = "main_genre", nullable = false, length = 48)
    private String mainGenre;

    @Column(name = "sub_genre", nullable = false, length = 48)
    private String subGenre;

    @Column(name = "type", nullable = false, length = 48)
    private String type;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "number_of_people_rated")
    private Integer numberOfPeopleRated;

    @Column(name = "url", nullable = false, length = 2048)
    private String url;
}
