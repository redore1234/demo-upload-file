package com.example.demouploadcsv.service.implement;

import com.example.demouploadcsv.domain.Tutorial;
import com.example.demouploadcsv.helper.CSVHelper;
import com.example.demouploadcsv.repository.TutorialRepository;
import com.example.demouploadcsv.service.CSVService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * The type Csv service.
 */
@Service
@Slf4j
public class CSVServiceImpl implements CSVService {
    @Autowired
    private TutorialRepository tutorialRepository;

    public void save(MultipartFile file) {
        try {
            List<Tutorial> tutorials = CSVHelper.csvToTutorial(file.getInputStream());
            tutorialRepository.saveAll(tutorials);
        } catch (IOException e) {
            log.error("Fail to store csv data:", e.getMessage());
        }
    }

    @Override
    public ByteArrayInputStream load() {
        List<Tutorial> tutorials = tutorialRepository.findAll();
        return  CSVHelper.tutorialToCSV(tutorials);
    }
}
