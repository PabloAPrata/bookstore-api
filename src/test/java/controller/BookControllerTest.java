package controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.sages.catalog.bookstore_api.BookstoreApiApplication;
import br.com.sages.catalog.bookstore_api.domain.Book;
import br.com.sages.catalog.bookstore_api.dto.BookDTO;
import br.com.sages.catalog.bookstore_api.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreApiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookControllerTest {

    private static final Long ID = 1L;
    private static final String TITLE = "Title TEST";
    private static final String AUTHOR = "Arthur Conan Doyle";
    private static final String GENRE = "Arts, Film & Photography";
    private static final String SUBGENRE = "Crafts, Hobbies & Practical Interests";
    private static final String TYPE = "Audible Audiobook";
    private static final Double PRICE = 135.00;
    private static final Double RATING = 4.5;
    private static final Integer NUMBEROFPEOPLERATED = 9994;
    private static final String URLBOOK = "https://www.amazon.in/Complete-Novels-Sherlock-Holmes/dp/8175994312/ref=zg_bs_g_1318054031_d_sccl_1/000-0000000-0000000?psc=1";

    private static final String URL = "/books";

    @MockBean
    BookService service;

    @Autowired
    MockMvc mvc;

    @Test
    public void testSave() throws Exception {

        BDDMockito.given(service.save(Mockito.any(Book.class))).willReturn(getMockBook());

        mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, TITLE, AUTHOR, GENRE, SUBGENRE, TYPE, PRICE, RATING, NUMBEROFPEOPLERATED, URLBOOK))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(ID))
                .andExpect(jsonPath("$.data.title").value(TITLE))
                .andExpect(jsonPath("$.data.author").value(AUTHOR))
                .andExpect(jsonPath("$.data.mainGenre").value(GENRE))
                .andExpect(jsonPath("$.data.subGenre").value(SUBGENRE))
                .andExpect(jsonPath("$.data.type").value(TYPE))
                .andExpect(jsonPath("$.data.price").value(PRICE))
                .andExpect(jsonPath("$.data.rating").value(RATING))
                .andExpect(jsonPath("$.data.numberOfPeopleRated").value(NUMBEROFPEOPLERATED))
                .andExpect(jsonPath("$.data.url").value(URLBOOK));

    }

    @Test
    public void testSaveInvalidBook() throws JsonProcessingException, Exception {
        mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, TITLE, AUTHOR, GENRE, SUBGENRE, TYPE, PRICE, RATING, NUMBEROFPEOPLERATED, "ISSO NAUM EH UMA URL"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0]").value("URL is invalid."));
    }

    public Book getMockBook() {
        Book book = new Book();
        book.setId(ID);
        book.setTitle(TITLE);
        book.setAuthor(AUTHOR);
        book.setMainGenre(GENRE);
        book.setSubGenre(SUBGENRE);
        book.setType(TYPE);
        book.setPrice(PRICE);
        book.setRating(RATING);
        book.setNumberOfPeopleRated(NUMBEROFPEOPLERATED);
        book.setUrl(URLBOOK);

        return book;
    }

    public String getJsonPayload(Long id, String title, String author, String genre, String subGenre, String type, Double price, Double rating, Integer numberOfPeopleRated, String url) throws JsonProcessingException {
        BookDTO dto = new BookDTO();
        dto.setId(id);
        dto.setTitle(title);
        dto.setAuthor(author);
        dto.setMainGenre(genre);
        dto.setSubGenre(subGenre);
        dto.setType(type);
        dto.setPrice(price);
        dto.setRating(rating);
        dto.setNumberOfPeopleRated(numberOfPeopleRated);
        dto.setUrl(url);

        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(dto);
    }
}
