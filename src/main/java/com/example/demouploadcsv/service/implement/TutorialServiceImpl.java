package com.example.demouploadcsv.service.implement;

import com.example.demouploadcsv.domain.Tutorial;
import com.example.demouploadcsv.dto.TutorialDTO;
import com.example.demouploadcsv.repository.TutorialRepository;
import com.example.demouploadcsv.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorialServiceImpl implements TutorialService {
    @Autowired
    private TutorialRepository tutorialRepository;

    @Override
    public Tutorial addTutorial(TutorialDTO request) {
        Tutorial newTutorial = Tutorial.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .published(request.isPublished())
                .build();

        return tutorialRepository.save(newTutorial);
    }

    @Override
    public List<Tutorial> getAllTutorials() {
        return tutorialRepository.findAll();
    }
}
