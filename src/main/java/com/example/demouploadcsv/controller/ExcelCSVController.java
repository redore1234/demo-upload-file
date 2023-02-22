package com.example.demouploadcsv.controller;

import com.example.demouploadcsv.domain.Tutorial;
import com.example.demouploadcsv.dto.ResponseDTO;
import com.example.demouploadcsv.dto.TutorialDTO;
import com.example.demouploadcsv.helper.CSVHelper;
import com.example.demouploadcsv.helper.ExcelHelper;
import com.example.demouploadcsv.helper.PDFHelper;
import com.example.demouploadcsv.service.CSVService;
import com.example.demouploadcsv.service.ExcelService;
import com.example.demouploadcsv.service.TutorialService;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * The type Excel csv controller.
 */
@RestController
@RequestMapping(path = "api/v1/tutorial")
@Slf4j
public class ExcelCSVController {
    @Autowired
    private ExcelService excelService;

    @Autowired
    private CSVService csvService;

    @Autowired
    private TutorialService tutorialService;

    /**
     * Upload file response entity.
     *
     * @param file the file
     * @return the response entity
     */
    @PostMapping("/upload")
    public ResponseEntity<ResponseDTO> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            if (ExcelHelper.hasExcelFormat(file)) {
                excelService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(message));
            } else if (CSVHelper.hasCSVFormat(file)) {
                csvService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(message));
            }
        }  catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDTO(message));
        }

        message = "Please upload an excel or csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(message));
    }

    /**
     * Gets all tutorials.
     *
     * @return the all tutorials
     */
    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials() {
        try {
            List<Tutorial> tutorials = tutorialService.getAllTutorials();

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets file excel.
     *
     * @return the file
     */
    @GetMapping("/download-excel")
    public ResponseEntity<Resource> getFileExcel() throws IOException {
        String fileName = "tutorial.xlsx";
        InputStreamResource file = new InputStreamResource(excelService.load());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

    /**
     * Gets file csv.
     *
     * @return the file
     */
    @GetMapping("/download-csv")
    public ResponseEntity<Resource> getFileCSV() {
        String fileName = "tutorial.csv";
        InputStreamResource file = new InputStreamResource(csvService.load());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @GetMapping(path = "/export-pdf")
    public ResponseEntity<InputStreamResource> exportToPDF() throws DocumentException {
        ByteArrayInputStream byteArrayInputStream = PDFHelper.generatePDF(tutorialService.getAllTutorials());
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD_HH-mm-ss");
        String currentDateTime = dateFormat.format(new Date());
        String fileName = "Tutorial_" + currentDateTime + ".pdf";

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + fileName)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(byteArrayInputStream));
    }

    @PostMapping
    public ResponseEntity<Tutorial> createTutorial(@Validated @RequestBody TutorialDTO tutorialDTO) {
        Tutorial tutorial = tutorialService.addTutorial(tutorialDTO);
        return new ResponseEntity<>(tutorial, HttpStatus.CREATED);
    }


}
