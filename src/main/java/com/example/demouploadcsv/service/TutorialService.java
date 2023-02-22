package com.example.demouploadcsv.service;

import com.example.demouploadcsv.domain.Tutorial;
import com.example.demouploadcsv.dto.TutorialDTO;

import java.util.List;

/**
 * The interface Tutorial service.
 */
public interface TutorialService {
    /**
     * Add tutorial.
     *
     * @param tutorial the tutorial
     */
    Tutorial addTutorial(TutorialDTO tutorial);

    /**
     * Gets all tutorials.
     *
     * @return the all tutorials
     */
    List<Tutorial> getAllTutorials();
}
