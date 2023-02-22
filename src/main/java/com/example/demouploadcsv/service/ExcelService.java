package com.example.demouploadcsv.service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface ExcelService {
    /**
     * Save.
     *
     * @param file the file
     */
    void save(MultipartFile file);

    ByteArrayInputStream load() throws IOException;
}
