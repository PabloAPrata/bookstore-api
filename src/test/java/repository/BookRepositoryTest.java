package repository;

import br.com.sages.catalog.bookstore_api.BookstoreApiApplication;
import br.com.sages.catalog.bookstore_api.domain.Book;

import br.com.sages.catalog.bookstore_api.repository.BookRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreApiApplication.class)
public class BookRepositoryTest {

//    private static final Long ID = 13L;
    private static final String GENRE = "Arts, Film & Photography";
    private static final String AUTHOR = "Arthur Conan Doyle";

    @Autowired
    BookRepository repository;

    @Before
    public void setUp() {
        Book book = new Book();
        book.setTitle("Setting up a book");
        book.setAuthor(AUTHOR);
        book.setMainGenre(GENRE);
        book.setSubGenre("Cinema & Broadcast");
        book.setType("Paperback");
        book.setPrice(BigDecimal.valueOf(169.00));
        book.setRating(4.4);
        book.setNumberOfPeopleRated(19923);
        book.setUrl("https://www.amazon.in/Complete-Novels-Sherlock-Holmes/dp/8175994312/ref=zg_bs_g_1318054031_d_sccl_1/000-0000000-0000000?psc=1");

        repository.save(book);
    }

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void testSave() {
        Book book = new Book();
        book.setTitle("Testing");
        book.setAuthor("Arthur Conan Doyle");
        book.setMainGenre("Arts, Film & Photography");
        book.setSubGenre("Cinema & Broadcast");
        book.setType("Paperback");
        book.setPrice(BigDecimal.valueOf(169.00));
        book.setRating(4.4);
        book.setNumberOfPeopleRated(19923);
        book.setUrl("https://www.amazon.in/Complete-Novels-Sherlock-Holmes/dp/8175994312/ref=zg_bs_g_1318054031_d_sccl_1/000-0000000-0000000?psc=1");

        Book response = repository.save(book);

        assertNotNull(response);
    }

    @Test
    public void testFindByGenre() {
        Optional<List<Book>> response = repository.findByMainGenreEquals(GENRE);

        assertTrue(response.isPresent());

        List<Book> books = response.get();
        assertFalse(books.isEmpty());

        boolean containsAuthor = books.stream().anyMatch(book -> GENRE.equals(book.getMainGenre()));
        assertTrue(containsAuthor);
    }

    @Test
    public void testFindByAuthor() {
        Optional<List<Book>> response = repository.findByAuthorEquals(AUTHOR);

        assertTrue(response.isPresent());

        List<Book> books = response.get();
        assertFalse(books.isEmpty());

        boolean containsAuthor = books.stream().anyMatch(book -> AUTHOR.equals(book.getAuthor()));
        assertTrue(containsAuthor);
    }
}
