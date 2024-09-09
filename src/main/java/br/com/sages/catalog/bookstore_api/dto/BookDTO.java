package br.com.sages.catalog.bookstore_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

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
    private String price;

    private Double rating;

    private Integer numberOfPeopleRated;

    @NotBlank(message = "Enter an URL.")
    @URL(message = "URL is invalid.")
    private String url;
}
