package br.com.sages.catalog.bookstore_api.repository;

import br.com.sages.catalog.bookstore_api.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<List<Book>> findByMainGenreEquals(String mainGenre);

    Optional<List<Book>> findByAuthorEquals(String author);
}
