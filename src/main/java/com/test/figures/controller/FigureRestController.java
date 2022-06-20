package com.test.figures.controller;


import com.test.figures.dto.FigureDto;
import com.test.figures.dto.FigureDtoExtended;
import com.test.figures.model.CommandFigure;
import com.test.figures.service.figures.FigureService;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/figures")
public class FigureRestController {
    private final FigureService figureService;

    @GetMapping
    public ResponseEntity getFigures(Principal principal) {
        return principal == null
                ? new ResponseEntity(figureService.getAllFigure().stream().map(FigureDto::new), HttpStatus.OK)
                : new ResponseEntity(figureService.getAllFigure().stream().map(FigureDtoExtended::new), HttpStatus.OK);
    }

    @PostMapping
    @Secured({"ROLE_ADMIN", "ROLE_CREATOR", "ROLE_USER"})
    public ResponseEntity createFigures(@Valid @RequestBody CommandFigure commandFigure) throws TemplateException, MessagingException, IOException {
        return new ResponseEntity(new FigureDtoExtended(figureService.save(commandFigure)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_CREATOR"})
    public ResponseEntity changeFigures(@PathVariable("id") int id, @Valid @RequestBody CommandFigure commandFigure) {
        return new ResponseEntity(new FigureDtoExtended(figureService.putNewValuesAndSave(id, commandFigure)), HttpStatus.OK);
    }
}
