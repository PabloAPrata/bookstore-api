package service;

import br.com.sages.catalog.bookstore_api.BookstoreApiApplication;
import br.com.sages.catalog.bookstore_api.domain.Book;
import br.com.sages.catalog.bookstore_api.repository.BookRepository;
import br.com.sages.catalog.bookstore_api.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreApiApplication.class)
@ActiveProfiles("test")
public class BookServiceTest {

    @MockBean
    BookRepository repository;

    @Autowired
    BookService service;

    @Before
    public void setUp() {
        List<Book> books = List.of(new Book());

        BDDMockito.given(repository.findByAuthorEquals(Mockito.anyString())).willReturn(Optional.of(books));
    }

    @Test
    public void testFindByAuthor() {
        Optional<List<Book>> books = service.findByAuthor("Arthur Conan Doyle");

        assertTrue(books.isPresent());
    }

    @Test
    public void testFindByMainGenre() {
        Optional<List<Book>> books = service.findByMainGenre("Arts, Film & Photography");
    }
}
