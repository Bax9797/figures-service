package com.test.figures.figures;


import com.test.figures.creator.FacadeFigureCreator;
import com.test.figures.entity.figures.Figure;
import com.test.figures.entity.figures.Square;
import com.test.figures.entity.user.User;
import com.test.figures.mail.EmailService;
import com.test.figures.model.CommandFigure;
import com.test.figures.repository.figures.FigureRepository;
import com.test.figures.service.figures.FigureService;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class FigureServiceTest {

    private FigureService figureService;
    private FacadeFigureCreator facadeFigure;

    private FigureRepository figureRepository;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        facadeFigure = mock(FacadeFigureCreator.class);
        figureRepository = mock(FigureRepository.class);
        emailService = mock(EmailService.class);
        figureService = new FigureService(figureRepository, emailService, facadeFigure);

    }

    @Test
    void shouldSave() throws MessagingException, TemplateException, IOException {
        when(figureRepository.saveAndFlush(any(Figure.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        doNothing().when(emailService).sendMessage(any());
        Figure square = new Square().setWidth(10.0);
        when(facadeFigure.getFigure(any())).thenReturn(square);
        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("underTest");

        CommandFigure commandFigure = new CommandFigure().setName("Square").setParameters(Map.of("width", 10.0));
        Figure expected = new Square().setWidth(10.0).setCreatedBy(mockPrincipal.getName()).setCreatedDate(LocalDate.now())
                .setLastModifiedBy(mockPrincipal.getName()).setLastModifiedDate(LocalDate.now()).setVersion(0);
        Figure given = figureService.save(commandFigure);
        assertEquals(expected, given);
    }

    @Test
    void ShouldSetById() {
        User user = new User().setUsername("underTest");
        Figure expected = new Square().setWidth(10.0).setCreatedBy(user.getUsername()).setCreatedDate(LocalDate.now())
                .setLastModifiedBy(user.getUsername()).setLastModifiedDate(LocalDate.now()).setVersion(0);
        when(figureRepository.findById(anyInt())).thenReturn(Optional.of(expected));
        Figure given = figureService.getById(1);
        assertEquals(expected, given);
    }

    @Test
    void shouldGetAllFigure() {
        User user = new User().setUsername("underTest");
        Figure underTest = new Square().setWidth(10.0).setCreatedBy(user.getUsername()).setCreatedDate(LocalDate.now())
                .setLastModifiedBy(user.getUsername()).setLastModifiedDate(LocalDate.now()).setVersion(0);
        when(figureRepository.findAll()).thenReturn(Arrays.asList(underTest));

        List<Figure> expected = Arrays.asList(underTest);
        List<Figure> given = figureService.getAllFigure();
        assertEquals(expected, given);
    }
}