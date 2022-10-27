package com.mediscreen.risk.service;


import com.mediscreen.risk.constant.DiabetesTrigger;
import com.mediscreen.risk.constant.RiskLevels;
import com.mediscreen.risk.model.Note;
import com.mediscreen.risk.model.Patient;
import com.mediscreen.risk.model.Rapport;
import com.mediscreen.risk.proxy.NoteProxy;
import com.mediscreen.risk.proxy.PatientProxy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
@Slf4j
public class RapportService {

    private final NoteProxy noteProxy;
    private final PatientProxy patientProxy;


    public RapportService(NoteProxy noteProxy, PatientProxy patientProxy) {
        this.noteProxy = noteProxy;
        this.patientProxy = patientProxy;
    }


    public Rapport getPatientRapport(Long patientId) {
        Patient patient = getPatient(patientId);

        int patientAge = getAge(patient.getBirthDate());

        List<Note> notePatientList = getPatientNotes(patientId);
        int patientTriggers = getPatientTriggers(notePatientList);

        String riskDiabeteLevel = getRiskDiabeteLevel(patientTriggers, patientAge, patient.getGender());

        return new Rapport(patient, patientAge, riskDiabeteLevel);
    }

    public int getAge(final LocalDate birthDate) {

        LocalDate date = LocalDate.now();
        int age = Period.between(birthDate, date).getYears();

        if (birthDate.isAfter(date)) {
            throw new IllegalArgumentException("La date de naissance n'est pas valide " + " - doit être inférieure à la date actuelle");
        } else if (age == 0) {
            age++;
        }

        return age;
    }

    public Patient getPatient(final Long patientId) {

        return patientProxy.findPatientById(patientId);
    }


    public Patient getPatient(String lastName, String firstName) {
        return patientProxy.findPatientByLastNameAndFirstName(lastName, firstName);
    }

    public List<Note> getPatientNotes(final Long patientId) {

        return noteProxy.findAllNote(patientId);
    }

    public int getPatientTriggers(final List<Note> noteList) {

        EnumSet<DiabetesTrigger> diabetesTriggers = EnumSet
                .allOf(DiabetesTrigger.class);

        List<DiabetesTrigger> patientTriggers = new ArrayList<>();
        diabetesTriggers.forEach(diabetesTrigger -> {
            noteList.forEach(note -> {
                if (StringUtils.containsIgnoreCase(
                        note.getNote(),
                        diabetesTrigger.getTrigger()) &&
                        !patientTriggers.contains(diabetesTrigger)) {
                    patientTriggers.add(diabetesTrigger);
                }
            });
        });

        return patientTriggers.size();
    }

    public String getRiskDiabeteLevel(final int triggers, final int patientAge, final String sexe) {

        String diabetesAssessment = RiskLevels.NONE.getRiskLevel();

        if ((((sexe.equals("F")) && patientAge < 30 && triggers >= 7) || ((sexe.equals("M")) && patientAge < 30 && triggers >= 5)) || (patientAge >= 30 && triggers >= 8)) {
            diabetesAssessment = RiskLevels.EARLY_ONSET.getRiskLevel();

        } else if ((sexe.equals("M") && patientAge < 30 && triggers >= 3) || ((sexe.equals("F")) && patientAge < 30 && triggers >= 4) || (patientAge >= 30 && triggers >= 6)) {
            diabetesAssessment = RiskLevels.IN_DANGER.getRiskLevel();

        } else if ((patientAge >= 30 && triggers >= 2)) {
            diabetesAssessment = RiskLevels.BORDERLINE.getRiskLevel();
        }

        return diabetesAssessment;
    }
}
