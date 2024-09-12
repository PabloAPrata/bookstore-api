package br.com.sages.catalog.bookstore_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for Book")
public class BookDTO {

    @Schema(description = "ID of the book")
    private Long id;

    @NotBlank(message = "Enter a title.")
    @Size(min = 2, max = 500, message = "The title must contain between {min} and {max} characters.")
    @Schema(description = "Title of the book")
    private String title;

    @NotBlank(message = "Enter an author.")
    @Size(min = 2, max = 128, message = "The author name must contain between {min} and {max} characters.")
    @Schema(description = "Author of the book")
    private String author;

    @NotBlank(message = "Enter a main genre.")
    @Size(min = 2, max = 48, message = "The main genre must contain between {min} and {max} characters.")
    @Schema(description = "Main genre of the book")
    private String mainGenre;

    @NotBlank(message = "Enter a sub genre.")
    @Size(min = 2, max = 48, message = "The sub genre must contain between {min} and {max} characters.")
    @Schema(description = "Sub genre of the book")
    private String subGenre;

    @NotBlank(message = "Enter a type.")
    @Size(min = 2, max = 48, message = "The type must contain between {min} and {max} characters.")
    @Schema(description = "Type of the book")
    private String type;

    @NotNull(message = "Enter a price.")
    @Schema(description = "Price of the book")
    private BigDecimal price;

    @DecimalMin(value = "0.0", inclusive = true, message = "The rating must be at least {value}.")
    @DecimalMax(value = "5.0", inclusive = true, message = "The rating must be at most {value}.")
    @Schema(description = "Rating of the book")
    private Double rating;

    @DecimalMin(value = "0.0", inclusive = true, message = "The rating must be at least {value}.")
    @Schema(description = "Number of people who rated the book")
    private Integer numberOfPeopleRated;

    @NotBlank(message = "Enter an URL.")
    @URL(message = "URL is invalid.")
    @Schema(description = "URL of the book")
    private String url;
}
