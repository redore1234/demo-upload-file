package com.example.demouploadcsv.service.implement;

import com.example.demouploadcsv.domain.Tutorial;
import com.example.demouploadcsv.helper.ExcelHelper;
import com.example.demouploadcsv.repository.TutorialRepository;
import com.example.demouploadcsv.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ExcelServiceImpl implements ExcelService {
    @Autowired
    private TutorialRepository tutorialRepository;

    @Override
    public void save(MultipartFile file) {
        try {
            List<Tutorial> tutorials = ExcelHelper.excelToTutorials(file.getInputStream());
            tutorialRepository.saveAll(tutorials);
        } catch (IOException e) {
            log.error("Fail to store excel data:", e.getMessage());
        }
    }

    @Override
    public ByteArrayInputStream load() throws IOException{
        List<Tutorial> tutorials = tutorialRepository.findAll();
        return ExcelHelper.tutorialsToExcel(tutorials);
    }
}
