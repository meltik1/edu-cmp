package com.edu_netcracker.cmp.notificationEngine.sessionImpl;

import com.edu_netcracker.cmp.notificationEngine.ITemplate;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

@Component
@NoArgsConstructor
public class TemplateRealisation implements ITemplate {
    private final MustacheFactory MF = new DefaultMustacheFactory();

    private String template;
    private String macrosTemplate;
    private String theme;

    @Override
    public String getTemplate() {
        return this.template;
    }

    @Override
    public void applyParams(Map<String, String> params) {
        StringWriter stringWriter = new StringWriter();
        Mustache mustache = MF.compile(new StringReader(macrosTemplate), "template");
        mustache.execute(stringWriter, params);
        this.template = stringWriter.toString();
    }

    @Override
    public void setTemplate(String template) {
        this.macrosTemplate = template;
    }

    @Override
    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public String getTheme() {
        return this.theme;
    }
}
