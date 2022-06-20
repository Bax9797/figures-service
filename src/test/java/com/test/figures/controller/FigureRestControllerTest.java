package com.test.figures.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.figures.model.CommandFigure;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Map;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@RequiredArgsConstructor
class FigureRestControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    void shouldGetFiguresWithoutAuthorization() throws Exception {
        mockMvc.perform(get("/figures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").value("SQUARE"))
                .andExpect(jsonPath("$[0].parameters.width").value("10.0"));
    }

    @Test
    @WithUserDetails(value = "test1")
    void shouldGetFiguresWithAuthorization() throws Exception {
        mockMvc.perform(get("/figures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").value("SQUARE"))
                .andExpect(jsonPath("$[0].parameters.width").value("10.0"))
                .andExpect(jsonPath("$[0].createdBy").value("test1"))
                .andExpect(jsonPath("$[0].createdDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[0].version").value("0"))
                .andExpect(jsonPath("$[0].lastModifiedBy").value("test1"))
                .andExpect(jsonPath("$[0].lastModifiedDate").value(LocalDate.now().toString()));
    }

    @Test
    @WithUserDetails(value = "test1")
    void shouldCreateFiguresWithAuthorization() throws Exception {
        CommandFigure underTest = new CommandFigure().setName("SQUARE").setParameters(Map.of("width", 10.0));
        String content = objectMapper.writeValueAsString(underTest);
        mockMvc.perform(post("/figures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("SQUARE"))
                .andExpect(jsonPath("$.parameters.width").value("10.0"))
                .andExpect(jsonPath("$.createdBy").value("test1"))
                .andExpect(jsonPath("$.createdDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.version").value("0"))
                .andExpect(jsonPath("$.lastModifiedBy").value("test1"))
                .andExpect(jsonPath("$.lastModifiedDate").value(LocalDate.now().toString()));

    }

    @WithAnonymousUser
    @Test
    void shouldNotCreateFiguresWithNotAllowedAuthorization() throws Exception {
        CommandFigure underTest = new CommandFigure().setName("SQUARE").setParameters(Map.of("width", 10.0));
        String content = objectMapper.writeValueAsString(underTest);
        mockMvc.perform(post("/figures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @WithUserDetails(value = "test1")
    @Test
    void shouldNotCreateFiguresWithAuthorizationAndThrowErrorFigureNotExists() throws Exception {
        CommandFigure underTest = new CommandFigure().setName("test").setParameters(Map.of("width", 100.0));
        String content = objectMapper.writeValueAsString(underTest);
        mockMvc.perform(post("/figures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("METHOD_ARGUMENT_NOT_VALID_EXCEPTION"))
                .andExpect(jsonPath("$.errors[0]").value("ERROR_FIGURE_NOT_EXISTS"));
    }

    @WithUserDetails(value = "test1")
    @Test
    void shouldNotCreateFiguresWithAuthorizationAndThrowErrorFigureParametersNotExists() throws Exception {
        CommandFigure underTest = new CommandFigure().setName("SQUARE").setParameters(Map.of("test", 100.0));
        String content = objectMapper.writeValueAsString(underTest);
        mockMvc.perform(post("/figures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("METHOD_ARGUMENT_NOT_VALID_EXCEPTION"))
                .andExpect(jsonPath("$.errors[0]").value("ERROR_FIGURE_NOT_EXISTS"));
    }

    @WithUserDetails(value = "test1")
    @Test
    void shouldNotCreateFiguresWithAuthorizationAndThrowErrorFigureParametersNotAllowed() throws Exception {
        CommandFigure underTest = new CommandFigure().setName("SQUARE").setParameters(Map.of("width", -100.0));
        String content = objectMapper.writeValueAsString(underTest);
        mockMvc.perform(post("/figures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("METHOD_ARGUMENT_NOT_VALID_EXCEPTION"))
                .andExpect(jsonPath("$.errors[0]").value("ERROR_FIGURE_PARAMETERS_NOT_ALLOWED"));
    }

    @WithUserDetails(value = "test1")
    @Test
    void shouldChangeFiguresWithAuthorization() throws Exception {
        CommandFigure underTest = new CommandFigure().setName("SQUARE").setParameters(Map.of("width", 100.0));
        String content = objectMapper.writeValueAsString(underTest);
        mockMvc.perform(put("/figures/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("100"))
                .andExpect(jsonPath("$.name").value("SQUARE"))
                .andExpect(jsonPath("$.parameters.width").value("100.0"))
                .andExpect(jsonPath("$.createdBy").value("test1"))
                .andExpect(jsonPath("$.createdDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.version").value("1"))
                .andExpect(jsonPath("$.lastModifiedBy").value("test1"))
                .andExpect(jsonPath("$.lastModifiedDate").value(LocalDate.now().toString()));
    }

    @WithAnonymousUser
    @Test
    void shouldNotChangeFiguresWithAnonymousUser() throws Exception {
        CommandFigure underTest = new CommandFigure().setName("SQUARE").setParameters(Map.of("width", 10.0));
        String content = objectMapper.writeValueAsString(underTest);
        mockMvc.perform(put("/figures/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @WithUserDetails(value = "test1")
    @Test
    void shouldNotChangeFiguresWithAuthorizationAndThrowErrorFigureNotExists() throws Exception {
        CommandFigure underTest = new CommandFigure().setName("test").setParameters(Map.of("width", 100.0));
        String content = objectMapper.writeValueAsString(underTest);
        mockMvc.perform(put("/figures/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("METHOD_ARGUMENT_NOT_VALID_EXCEPTION"))
                .andExpect(jsonPath("$.errors[0]").value("ERROR_FIGURE_NOT_EXISTS"));

    }

    @WithUserDetails(value = "test1")
    @Test
    void shouldNotChangeFiguresWithAuthorizationAndThrowErrorParametersFigureNotNotAllowed() throws Exception {
        CommandFigure underTest = new CommandFigure().setName("SQUARE").setParameters(Map.of("width", -100.0));
        String content = objectMapper.writeValueAsString(underTest);
        mockMvc.perform(put("/figures/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("METHOD_ARGUMENT_NOT_VALID_EXCEPTION"))
                .andExpect(jsonPath("$.errors[0]").value("ERROR_FIGURE_PARAMETERS_NOT_ALLOWED"));

    }

    @WithUserDetails(value = "test1")
    @Test
    void shouldNotChangeFiguresWithAuthorizationAndThrowNotFoundException() throws Exception {
        CommandFigure underTest = new CommandFigure().setName("SQUARE").setParameters(Map.of("width", 100.0));
        String content = objectMapper.writeValueAsString(underTest);
        mockMvc.perform(put("/figures/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("FIGURE_NOT_FOUND"))
                .andExpect(jsonPath("$.id").value("9999"));
    }
}