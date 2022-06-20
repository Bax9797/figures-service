package com.test.figures.mail;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

@Service
@RequiredArgsConstructor
public class TemplateService {
    private final Configuration configuration;

    public String fillTemplate(String templateName, Object map) throws IOException, TemplateException {
        ClassPathResource resource = new ClassPathResource("templates/" + templateName + ".ftl");
        Reader reader = new InputStreamReader(resource.getInputStream());
        return renderTemplate(templateName, map, reader);
    }

    public String renderTemplate(String templateName, Object map, Reader reader) throws IOException, TemplateException {
        Template template = new Template(templateName, reader, configuration);
        return processTemplate(template, map);
    }

    private String processTemplate(Template template, Object map) throws TemplateException, IOException {
        StringWriter writer = new StringWriter();
        template.process(map, writer);
        return writer.toString();
    }
}
