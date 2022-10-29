package com.mediscreen.risk.controller;

import com.mediscreen.risk.RiskApplication;
import com.mediscreen.risk.model.Patient;
import com.mediscreen.risk.model.Rapport;
import com.mediscreen.risk.service.RapportService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Slf4j
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK, classes={ RiskApplication.class })
public class RapportControllerTest {
    @MockBean
    private RapportService rapportService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getPatientRapportTest() throws Exception {

        Rapport rapport = new Rapport(
                new Patient(1L, "Ferguson", "Lucas",
                        LocalDate.of(1968,06,22), "M", "2 Warren Street ", "387-866-1399"),
                0, "BORDELINE");

        when(rapportService.getPatientRapport(1L)).thenReturn(rapport);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/assess/1")
                        .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

        String content = result.getResponse()
                .getContentAsString();

        assertThat(content).contains("BORDELINE");
        verify(rapportService).getPatientRapport(1L);
    }

}
