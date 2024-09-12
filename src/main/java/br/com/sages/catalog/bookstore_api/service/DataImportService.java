package br.com.sages.catalog.bookstore_api.service;

import java.io.IOException;

public interface DataImportService {
    void importCsv(String filePath) throws IOException;
}
