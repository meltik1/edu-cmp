package com.edu_netcracker.cmp.notificationEngine;

public interface NotificationService {
    String getName();

    void send(IUserMessageInfo userMessageInfo, ITemplate template);
}
