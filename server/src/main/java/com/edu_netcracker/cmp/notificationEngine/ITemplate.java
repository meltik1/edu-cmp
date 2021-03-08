package com.edu_netcracker.cmp.notificationEngine;

import java.util.Map;

public interface ITemplate {

    String getTemplate();
    void applyParams(Map<String, String> params);
    void setTemplate(String template);
}
