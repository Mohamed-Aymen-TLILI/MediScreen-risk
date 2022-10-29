package com.mediscreen.risk.service;

import com.mediscreen.risk.proxy.NoteProxy;
import com.mediscreen.risk.proxy.PatientProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AgeCalculatorTest {

    @InjectMocks
    private RapportService rapportService;

    @Mock
    private NoteProxy noteProxy;

    @Mock
    private PatientProxy patientProxy;


    @Test
    public void getAgeTest() {
        LocalDate birthDate = LocalDate.now().minusYears(25);

        int age = rapportService.getAge(birthDate);

        assertEquals(25, age);
    }

    @Test
    public void getAgeForLessThanAYearTest() {
        LocalDate birthDate = LocalDate.now().minusMonths(11);

        int age = rapportService.getAge(birthDate);

        assertEquals(1, age);
    }

    @Test
    public void getAgeForInvalidNullValueDateOfBirthInputTest() {
        LocalDate birthDate = null;

        assertThatNullPointerException()
                .isThrownBy(() -> rapportService.getAge(birthDate));
    }

    @Test
    public void getAgeForInvalidFutureDateOfBirthInputTest() {
        int age = 1;
        LocalDate birthDate = LocalDate.now().plusYears(age);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> rapportService.getAge(birthDate));
    }

    @Test
    public void testGetAgeForInvalidNegativeValueDateOfBirthInput() {
        int age = -1;
        LocalDate birthDate = LocalDate.now().minusYears(age);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> rapportService.getAge(birthDate));
    }

}
