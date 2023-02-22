package com.example.demouploadcsv.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Tutorial implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    private String description;
    private boolean published;

    public Tutorial(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }

    @Override
    public String toString() {
        return "Tutorial [" +
                "id=" + id +
                ", title='" + title +
                ", description='" + description +
                ", published='" + published +
                ']';
    }
}
