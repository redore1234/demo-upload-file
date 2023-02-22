package com.example.demouploadcsv.repository;

import com.example.demouploadcsv.domain.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Integer> {
}
