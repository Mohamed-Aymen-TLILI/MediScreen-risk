package com.mediscreen.risk.controller;

import com.mediscreen.risk.model.Rapport;
import com.mediscreen.risk.service.RapportService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Api(tags = "API des données du rapport")
@Slf4j
@RequestMapping("/api")
public class RapportController {

    private final RapportService rapportService;

    @Autowired
    public RapportController(RapportService rapportService) {
        this.rapportService = rapportService;
    }

    @GetMapping("/assess/{id}")
    public Rapport getPatientRapport(@PathVariable("id") final Long patientId) {

        log.debug(" *** Méthode GET /api/rapport/{id} ");

        Rapport rapport = rapportService.getPatientRapport(patientId);

        log.debug(" *** Rapport d'évaluation renvoyé avec succès");

        return rapport;
    }

}

