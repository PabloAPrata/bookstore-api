package br.com.sages.catalog.bookstore_api.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Long id;

    @NotBlank(message = "Enter a title.")
    @Size(min = 2, max = 500, message = "The title must contain between {min} and {max} characters.")
    private String title;

    @NotBlank(message = "Enter an author.")
    @Size(min = 2, max = 128, message = "The author name must contain between {min} and {max} characters.")
    private String author;

    @NotBlank(message = "Enter a main genre.")
    @Size(min = 2, max = 48, message = "The main genre must contain between {min} and {max} characters.")
    private String mainGenre;

    @NotBlank(message = "Enter a sub genre.")
    @Size(min = 2, max = 48, message = "The sub genre must contain between {min} and {max} characters.")
    private String subGenre;

    @NotBlank(message = "Enter a type.")
    @Size(min = 2, max = 48, message = "The type must contain between {min} and {max} characters.")
    private String type;

    @NotNull(message = "Enter a price.")
    private Double price;

    @DecimalMin(value = "0.0", inclusive = true, message = "The rating must be at least {value}.")
    @DecimalMax(value = "5.0", inclusive = true, message = "The rating must be at most {value}.")
    private Double rating;

    @DecimalMin(value = "0.0", inclusive = true, message = "The rating must be at least {value}.")
    private Integer numberOfPeopleRated;

    @NotBlank(message = "Enter an URL.")
    @URL(message = "URL is invalid.")
    private String url;
}
