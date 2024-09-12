package br.com.sages.catalog.bookstore_api.controller;

import br.com.sages.catalog.bookstore_api.controller.docs.BookControllerDoc;
import br.com.sages.catalog.bookstore_api.domain.Book;
import br.com.sages.catalog.bookstore_api.dto.BookDTO;
import br.com.sages.catalog.bookstore_api.response.Response;
import br.com.sages.catalog.bookstore_api.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("books")
public class BookController implements BookControllerDoc {

    @Autowired
    private BookService service;


    @PostMapping
    public ResponseEntity<Response<BookDTO>> create(@Valid @RequestBody BookDTO dto, BindingResult result) {

        Response<BookDTO> response = new Response<BookDTO>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Book book = service.save(this.convertDtoToEntity(dto));

        response.setData(this.convertEntityToDto(book));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<BookDTO>>> getAll() {
        Response<List<BookDTO>> response = new Response<List<BookDTO>>();

        List<Book> books = service.findAll();

        List<BookDTO> booksDTO = books.stream().map(this::convertEntityToDto)
                        .toList();

        response.setData(booksDTO);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<BookDTO>> getById(@PathVariable("id") Long id) {
        Response<BookDTO> response = new Response<>();

        Optional<Book> bookOptional = service.findById(id);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            response.setData(convertEntityToDto(book));
            return ResponseEntity.ok(response);
        } else {
            response.getErrors().add("Book " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<Response<List<BookDTO>>> getByGenre(@PathVariable("genre") String genre) {
        Response<List<BookDTO>> response = new Response<>();

        Optional<List<Book>> books = service.findByMainGenre(genre);

        if (books.isEmpty() || books.get().isEmpty()) {
            response.getErrors().add("No books found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        List<BookDTO> booksDTO = books.get().stream()
                .map(this::convertEntityToDto)
                .toList();

        response.setData(booksDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<Response<List<BookDTO>>> getByAuthor(@PathVariable("author") String author) {
        Response<List<BookDTO>> response = new Response<>();

        Optional<List<Book>> books = service.findByAuthor(author);

        if (books.isEmpty() || books.get().isEmpty()) {
            response.getErrors().add("No books found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        List<BookDTO> booksDTO = books.get().stream()
                .map(this::convertEntityToDto)
                .toList();

        response.setData(booksDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private Book convertDtoToEntity(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setMainGenre(dto.getMainGenre());
        book.setSubGenre(dto.getSubGenre());
        book.setType(dto.getType());
        book.setPrice(dto.getPrice());
        book.setRating(dto.getRating());
        book.setNumberOfPeopleRated(dto.getNumberOfPeopleRated());
        book.setUrl(dto.getUrl());

        return book;
    }

    private BookDTO convertEntityToDto(Book book) {

        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setMainGenre(book.getMainGenre());
        dto.setSubGenre(book.getSubGenre());
        dto.setType(book.getType());
        dto.setPrice(book.getPrice());
        dto.setRating(book.getRating());
        dto.setNumberOfPeopleRated(book.getNumberOfPeopleRated());
        dto.setUrl(book.getUrl());

        return dto;
    }
}
