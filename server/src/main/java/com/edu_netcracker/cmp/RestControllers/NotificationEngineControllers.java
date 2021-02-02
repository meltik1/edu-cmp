package com.edu_netcracker.cmp.RestControllers;

import com.edu_netcracker.cmp.notificationEngine.NotificationService;
import com.edu_netcracker.cmp.notificationEngine.telegramImpl.NotificationBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class NotificationEngineControllers  {

    @Autowired
    private NotificationService notificationService;

    public NotificationEngineControllers(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RequestMapping(value = "send")
    public void sendMessage() {
        notificationService.send(3L, "Э");
    }

    // Here will be excel parser
    @RequestMapping(value = "import-excel", method = RequestMethod.POST)
    public void excelImport(@RequestParam MultipartFile file) {

    }
}
