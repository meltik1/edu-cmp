package com.edu_netcracker.cmp.notificationEngine.sessionImpl;

import com.edu_netcracker.cmp.notificationEngine.ITemplate;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@NoArgsConstructor
public class TemplateRealisation implements ITemplate {
    private String template;

    @Override
    public String getTemplate() {
        return template;
    }

    @Override
    public void applyParams(Map<String, String> params) {

    }

    @Override
    public void setTemplate(String template) {
        this.template = template;
    }
}
