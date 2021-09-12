package com.edu_netcracker.cmp.notificationEngine;

import com.edu_netcracker.cmp.entities.Session;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface EAVInfoTransformer {
    void transformUserDataToEAV(Session session) throws JsonProcessingException;
}
