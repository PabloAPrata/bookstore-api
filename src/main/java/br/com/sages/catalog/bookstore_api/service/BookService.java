package br.com.sages.catalog.bookstore_api.service;

import br.com.sages.catalog.bookstore_api.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book save(Book book);

    List<Book> findAll();

    Optional<Book> findById(Long id);

    Optional<List<Book>> findByMainGenre(String mainGenre);

    Optional<List<Book>> findByAuthor (String author);
}
