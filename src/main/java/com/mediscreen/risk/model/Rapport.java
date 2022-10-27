package com.mediscreen.risk.model;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rapport implements Serializable {

    private Patient patient;

    private int patientAge;

    private String riskDiabeteLevel;

}

