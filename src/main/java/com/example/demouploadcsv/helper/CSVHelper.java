package com.example.demouploadcsv.helper;

import com.example.demouploadcsv.domain.Tutorial;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Csv helper.
 */
@Slf4j
public class CSVHelper {
    /**
     * The constant TYPE.
     */
    static final String TYPE = "text/csv";
    /**
     * The Header.
     */
    static String[] headers = {"ID", "Title", "Description", "Published"};

    /**
     * check if a file has CSV format or not
     *
     * @param file the file
     * @return the boolean
     */
    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }
    private enum csvHeader {
        /**
         * Title csv header.
         */
        Title,
        /**
         * Description csv header.
         */
        Description,
        /**
         * Published csv header.
         */
        Published
    }

    /**
     * read InputStream of a file, return a list
     *
     * @param inputStream the input stream
     * @return the list
     */
    public static List<Tutorial> csvToTutorial(InputStream inputStream) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            CSVParser csvParser = new CSVParser(br, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            List<Tutorial> tutorials = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Tutorial tutorial = new Tutorial(
                        csvRecord.get(csvHeader.Title),
                        csvRecord.get(csvHeader.Description),
                        Boolean.parseBoolean(csvRecord.get(csvHeader.Published))
                );
                tutorials.add(tutorial);
            }
            return tutorials;
        } catch (IOException e) {
            log.error("Fail to parse CSV file: ", e.getMessage());
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    /**
     * Process a list of Tutorials and return a ByteArrayInputStream for CSV
     *
     * @param tutorials the tutorials
     * @return the byte array input stream
     */
    public static ByteArrayInputStream tutorialToCSV(List<Tutorial> tutorials) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(outputStream),  CSVFormat.DEFAULT.withHeader(headers));

            // Data
            for (Tutorial tutorial : tutorials) {
                List<String> data = Arrays.asList(
                    String.valueOf(tutorial.getId()),
                    tutorial.getTitle(),
                    tutorial.getDescription(),
                    String.valueOf(tutorial.isPublished())
                );
                csvPrinter.printRecord(data); // print data to csv
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
