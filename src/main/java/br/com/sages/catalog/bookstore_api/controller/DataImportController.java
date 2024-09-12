package br.com.sages.catalog.bookstore_api.controller;

import br.com.sages.catalog.bookstore_api.response.Response;
import br.com.sages.catalog.bookstore_api.service.DataImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("load")
public class DataImportController {

    @Autowired
    private DataImportService service;

    @PostMapping("books")
    public ResponseEntity<Response<String>> populateDatabase() {
        Response<String> response = new Response<>();
        try {

            String filePath = "src/main/resources/data/Books_df.csv";
            service.importCsv(filePath);

            response.setData("Data successfully inserted into the database.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            response.getErrors().add("Failed to import data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
