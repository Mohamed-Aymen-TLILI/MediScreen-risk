package com.mediscreen.risk.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Note {

    private String id;

    private Long patientId;

    private String note;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateNote;

    public Note(Long patientId, String note) {
        this.patientId = patientId;
        this.note = note;
    }
}
