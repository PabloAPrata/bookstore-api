package br.com.sages.catalog.bookstore_api.service.impl;

import br.com.sages.catalog.bookstore_api.domain.Book;
import br.com.sages.catalog.bookstore_api.repository.BookRepository;
import br.com.sages.catalog.bookstore_api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository repository;

    @Override
    public Book save(Book book) {
        return repository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<List<Book>> findByMainGenre(String mainGenre) {
        return repository.findByMainGenreEquals(mainGenre);
    }

    @Override
    public Optional<List<Book>> findByAuthor(String author) {
        return repository.findByAuthorEquals(author);
    }
}
