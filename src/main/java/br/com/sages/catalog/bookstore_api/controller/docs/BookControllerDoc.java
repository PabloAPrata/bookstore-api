package br.com.sages.catalog.bookstore_api.controller.docs;

import br.com.sages.catalog.bookstore_api.dto.BookDTO;
import br.com.sages.catalog.bookstore_api.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Books")
public interface BookControllerDoc {

    @Operation(summary = "Register a book", description = "Returns the registered book with the assigned ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book has been registered", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Some parameter is incorrect", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)))
    })
    ResponseEntity<Response<BookDTO>> create(@Valid @RequestBody BookDTO dto, BindingResult result);

    @Operation(summary = "Brings a list with all the books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book list returned successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
    })
    ResponseEntity<Response<List<BookDTO>>> getAll();

    @Operation(summary = "Bring a single book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book returned successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)))
    })
    @Parameters({
            @Parameter(name = "id", description = "ID of the book to be retrieved", required = true),
    })
    ResponseEntity<Response<BookDTO>> getById(@PathVariable("id") Long id);

    @Operation(summary = "Bring a list of book by genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book list returned successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Any book found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)))
    })
    @Parameters({
            @Parameter(name = "genre", description = "Genre of the books to be retrieved", required = true),
    })
    ResponseEntity<Response<List<BookDTO>>> getByGenre(@PathVariable("genre") String genre);

    @Operation(summary = "Bring a list of book by author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book list returned successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Any book found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)))
    })
    @Parameters({
            @Parameter(name = "author", description = "Author of the books to be retrieved", required = true)
    })
    ResponseEntity<Response<List<BookDTO>>> getByAuthor(@PathVariable("author") String author);
}
