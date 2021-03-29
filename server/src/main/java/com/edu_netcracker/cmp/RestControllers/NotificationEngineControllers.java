package com.edu_netcracker.cmp.RestControllers;

import com.edu_netcracker.cmp.entities.HashMapConverter;
import com.edu_netcracker.cmp.notificationEngine.NotificationService;
import com.edu_netcracker.cmp.notificationEngine.parserImpl.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class NotificationEngineControllers  {



    @Autowired
    private NotificationService notificationService;

    @Autowired
    private FileHandler fileHandler;

    public NotificationEngineControllers(NotificationService notificationService) {
        this.notificationService = notificationService;
    }



    @RequestMapping(value = "send")
    public void sendMessage() {
        //notificationService.send(3L, "Э");
    }

    @RequestMapping(value = "send-file", method = RequestMethod.POST)
    public ResponseEntity<?> excelImport(@RequestParam MultipartFile file) {
        String response = this.fileHandler.handle(file);
        if (response == null || response.equals("")) {
            return new ResponseEntity<>("Could not handle file", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
