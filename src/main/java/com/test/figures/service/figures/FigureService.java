package com.test.figures.service.figures;

import com.test.figures.creator.FacadeFigureCreator;
import com.test.figures.entity.figures.Figure;
import com.test.figures.exception.figure.FigureNotFoundException;
import com.test.figures.mail.EmailService;
import com.test.figures.model.CommandFigure;
import com.test.figures.repository.figures.FigureRepository;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FigureService {
    private final FigureRepository figureRepository;
    private final EmailService emailService;
    private final FacadeFigureCreator facadeFigureCreator;

    public Figure save(CommandFigure commandFigure) throws TemplateException, MessagingException, IOException {
        Figure figure = facadeFigureCreator.getFigure(commandFigure);
        figureRepository.saveAndFlush(figure);
        isFigureTooLarge(figure);
        return figure;
    }

    public Figure getById(int id) {
        return figureRepository.findById(id).orElseThrow(() -> new FigureNotFoundException(id));
    }

    public List<Figure> getAllFigure() {
        return figureRepository.findAll();
    }

    private void isFigureTooLarge(Figure figure) throws TemplateException, IOException, MessagingException {
        if (!facadeFigureCreator.checkingTheAllowedAreaSize(figure))
            emailService.sendMessage(emailService.messageContent(figure));
    }

    public Figure putNewValuesAndSave(int id, CommandFigure commandFigure) {
        Figure figure = facadeFigureCreator.putNewValuesFacade(commandFigure, getById(id));
        figureRepository.saveAndFlush(figure);
        return figure;
    }
}
