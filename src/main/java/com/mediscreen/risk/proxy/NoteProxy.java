package com.mediscreen.risk.proxy;


import com.mediscreen.risk.model.Note;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "mediscreen-note", url = "${note.serviceUrl}")
public interface NoteProxy {

    @GetMapping("/patHistory")
    @ApiOperation(value = "Afficher la liste des notes des patients")
    List<Note> findAllNote(@RequestParam final Long patientId);
}
