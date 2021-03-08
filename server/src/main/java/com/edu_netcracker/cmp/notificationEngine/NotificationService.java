package com.edu_netcracker.cmp.notificationEngine;

public interface NotificationService {
    void send(IUserMessageInfo userMessageInfo, ITemplate template);
}
