package br.com.sages.catalog.bookstore_api.service.impl;

import br.com.sages.catalog.bookstore_api.domain.Book;
import br.com.sages.catalog.bookstore_api.repository.BookRepository;
import br.com.sages.catalog.bookstore_api.service.DataImportService;
import jakarta.transaction.Transactional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;

@Service
@Transactional
public class DataImportServiceImpl implements DataImportService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BookRepository repository;

    public void importCsv(String filePath) throws IOException {
        try (FileReader fileReader = new FileReader(Paths.get(filePath).toFile());
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                Book book = new Book();
                book.setTitle(record.get("Title"));
                book.setAuthor(record.get("Author"));
                book.setMainGenre(record.get("Main Genre"));
                book.setSubGenre(record.get("Sub Genre"));
                book.setType(record.get("Type"));

                // Optional fields
                String priceStr = record.get("Price");
                if (priceStr != null && !priceStr.isEmpty()) {
                    priceStr = priceStr.replace(",", "");
                    book.setPrice(new BigDecimal(priceStr));
                }

                String ratingStr = record.get("Rating");
                if (ratingStr != null && !ratingStr.isEmpty()) {
                    book.setRating(Double.parseDouble(ratingStr));
                }

                String numberOfPeopleRatedStr = record.get("No. of People rated");
                if (numberOfPeopleRatedStr != null && !numberOfPeopleRatedStr.isEmpty()) {
                    book.setNumberOfPeopleRated(Integer.parseInt(numberOfPeopleRatedStr));
                }

                book.setUrl(record.get("URLs"));

                repository.save(book);
            }
        }
    }

    }


