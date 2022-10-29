package com.mediscreen.risk.service;

import com.mediscreen.risk.RiskApplication;
import com.mediscreen.risk.model.Note;
import com.mediscreen.risk.model.Patient;
import com.mediscreen.risk.model.Rapport;
import com.mediscreen.risk.proxy.NoteProxy;
import com.mediscreen.risk.proxy.PatientProxy;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes={ RiskApplication.class })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PatientRapportTest {

    @InjectMocks
    private RapportService rapportService;

    @Mock
    private NoteProxy noteProximity;

    @Mock
    private PatientProxy patientProximity;


    @Test
    public void getPatientAssessmentForFergusonAsBorderlineTest() {

        Patient patient = new Patient(1L, "Ferguson", "Lucas",
                LocalDate.of(1968,06,22), "M", "2 Warren Street ", "387-866-1399");
        Note note1 = new Note("1", 1L, "The patient reports that he feels very well. Weight equal to or less than the recommended weight", LocalDate.of(1990,01,01));
        Note note2 = new Note("2", 1L, "Patient reports feeling tired during the day He also complains of muscle aches Laboratory tests indicating high microalbumin ", LocalDate.of(1991,01,01));
        Note note3 = new Note("3", 1L, "Patient says he doesn't feel that tired Smoker, quit within past 12 months Lab tests showing antibodies are high ", LocalDate.of(1990,01,01));
        List<Note> noteList = Arrays.asList(note1, note2, note3);
        when(noteProximity.findAllNote(1L)).thenReturn(noteList);
        when(patientProximity.findPatientById(1L)).thenReturn(patient);

        Rapport rapport = rapportService.getPatientRapport(1L);

        assertEquals("Borderline", rapport.getRiskDiabeteLevel());

    }

    @Test
    public void getPatientAssessmentForResAsBorderlineTest() {

        Patient patient = new Patient(1L, "Res", "Pippa",
                LocalDate.of(1952,9,27), "F", "745 West Valley Farms Drive ", "628-423-0993");
        Note note1 = new Note("1", 1L, "The patient reports that he feels a lot of stress at work He also complains that his hearing is abnormal lately. ", LocalDate.of(1990,01,01));
        Note note2 = new Note("2", 1L, "Patient reports having had a reaction to medication in the last 3 months He also notices that his hearing continues to be abnormal. ", LocalDate.of(1991,01,01));
        Note note3 = new Note("3", 1L, "Laboratory tests indicating high microalbumin", LocalDate.of(1990,01,01));
        Note note4 = new Note("4", 1L, "The patient declares that everything seems to be going well Lab reports hemoglobin A1C exceeds recommended level The patient declares that he has smoked for a long time ", LocalDate.of(1990,01,01));
        List<Note> noteList = Arrays.asList(note1, note2, note3, note4);
        when(noteProximity.findAllNote(1L)).thenReturn(noteList);
        when(patientProximity.findPatientById(1L)).thenReturn(patient);

        Rapport rapport = rapportService.getPatientRapport(1L);

        assertEquals("Borderline", rapport.getRiskDiabeteLevel());

    }

    @Test
    public void getPatientAssessmentForPatientTestNoneTest() {

        Patient patient = new Patient(1L, "TestNone", "Test",
                LocalDate.of(1952,12,31), "F", "1 Brookside St&phone", "100-222-3333");
        Note note1 = new Note("1", 1L, " Patient states that they are feeling terrific Weight at or below recommended level", LocalDate.of(1966,01,01));

        List<Note> noteList = Arrays.asList(note1);
        when(noteProximity.findAllNote(1L)).thenReturn(noteList);
        when(patientProximity.findPatientById(1L)).thenReturn(patient);

        Rapport rapport = rapportService.getPatientRapport(1L);

        assertEquals("Borderline", rapport.getRiskDiabeteLevel());

    }

    @Test
    public void getPatientAssessmentForPatientTestBorderlineTest() {

        Patient patient = new Patient(1L, "TestBorderline", "Test",
                LocalDate.of(1945,06,24), "M", "2 High St&phone", "200-333-4444");
        Note note1 = new Note("1", 1L, "Patient states that they are feeling a great deal of stress at work Patient also complains that their hearing seems Abnormal as of late", LocalDate.of(1966,01,01));
        Note note2 = new Note("2", 1L, "Patient states that they have had a Reaction to medication within last 3 months Patient also complains that their hearing continues to be problematic", LocalDate.of(1966,01,01));

        List<Note> noteList = Arrays.asList(note1, note2);
        when(noteProximity.findAllNote(1L)).thenReturn(noteList);
        when(patientProximity.findPatientById(1L)).thenReturn(patient);

        Rapport rapport = rapportService.getPatientRapport(1L);

        assertEquals("Borderline", rapport.getRiskDiabeteLevel());

    }

    @Test
    public void GetPatientAssessmentForPatientTestInDangerTest() {

        Patient patient = new Patient(1L, "TestInDanger", "Test",
                LocalDate.of(2004,06,18), "M", "3 Club Road&phone", "300-444-5555");
        Note note1 = new Note("1", 1L, "Patient states that they are short term Smoker", LocalDate.of(1966,01,01));
        Note note2 = new Note("2", 1L, "Patient states that they quit within last year Patient also complains that of Abnormal breathing spells Lab reports Cholesterol LDL high", LocalDate.of(1966,01,01));

        List<Note> noteList = Arrays.asList(note1, note2);

        when(noteProximity.findAllNote(1L)).thenReturn(noteList);
        when(patientProximity.findPatientById(1L)).thenReturn(patient);

        Rapport rapport = rapportService.getPatientRapport(1L);

        assertEquals("Borderline", rapport.getRiskDiabeteLevel());

    }

    @Test
    public void GetPatientAssessmentForPatientTestEarlyOnsetTest() {

        Patient patient = new Patient(1L, "TestEarlyOnset", "Test",
                LocalDate.of(2002,06,28), "F", "4 Valley Dr&phone=", "400-555-6666");
        Note note1 = new Note("1", 1L, "Patient states that walking up stairs has become difficult Patient also complains that they are having shortness of breath Lab results indicate Antibodies present elevated Reaction to medication", LocalDate.of(1966,01,01));
        Note note2 = new Note("2", 1L, "Patient states that they are experiencing back pain when seated for a long time", LocalDate.of(1966,01,01));
        Note note3 = new Note("3", 1L, "Patient states that they are a short term Smoker Hemoglobin A1C above recommended level", LocalDate.of(1966,01,01));
        Note note4 = new Note("4", 1L, "Patient states that Body Height, Body Weight, Cholesterol, Dizziness and Reaction", LocalDate.of(1966,01,01));

        List<Note> noteList = Arrays.asList(note1, note2, note3, note4);
        when(noteProximity.findAllNote(1L)).thenReturn(noteList);
        when(patientProximity.findPatientById(1L)).thenReturn(patient);

        Rapport rapport = rapportService.getPatientRapport(1L);

        assertEquals("Borderline", rapport.getRiskDiabeteLevel());

    }

}
