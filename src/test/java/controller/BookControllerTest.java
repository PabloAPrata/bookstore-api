package controller;

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

    private static final String TITLE = "Title TEST";
    private static final String GENRE = "Arts, Film & Photography";
    private static final String AUTHOR = "Arthur Conan Doyle";
    private static final String URL = "/books";

    @MockBean
    BookService service;

    @Autowired
    MockMvc mvc;

    @Test
    public void testSave() throws Exception {

        BDDMockito.given(service.save(Mockito.any(Book.class))).willReturn(getMockBook());

        mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    public Book getMockBook() {
        Book book = new Book();
        book.setTitle(TITLE);
        book.setAuthor(AUTHOR);
        book.setMainGenre(GENRE);
        book.setSubGenre("Cinema & Broadcast");
        book.setType("Paperback");
        book.setPrice(new BigDecimal("169.00"));
        book.setRating(4.4);
        book.setNumberOfPeopleRated(19923);
        book.setUrl("https://www.amazon.in/Complete-Novels-Sherlock-Holmes/dp/8175994312/ref=zg_bs_g_1318054031_d_sccl_1/000-0000000-0000000?psc=1");

        return book;
    }

    public String getJsonPayload() throws JsonProcessingException {
        BookDTO dto = new BookDTO();
        dto.setTitle(TITLE);
        dto.setAuthor(AUTHOR);
        dto.setMainGenre(GENRE);
        dto.setSubGenre("Cinema & Broadcast");
        dto.setType("Paperback");
        dto.setPrice(new BigDecimal("169.00"));
        dto.setRating(4.4);
        dto.setNumberOfPeopleRated(19923);
        dto.setUrl("https://www.amazon.in/Complete-Novels-Sherlock-Holmes/dp/8175994312/ref=zg_bs_g_1318054031_d_sccl_1/000-0000000-0000000?psc=1");

        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(dto);
    }
}
