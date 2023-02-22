package com.example.demouploadcsv.service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * The interface Csv service.
 */
public interface CSVService {
    /**
     * Save.
     *
     * @param file the file
     */
    void save(MultipartFile file);

    /**
     * Load byte array input stream.
     *
     * @return the byte array input stream
     */
    ByteArrayInputStream load();
}
