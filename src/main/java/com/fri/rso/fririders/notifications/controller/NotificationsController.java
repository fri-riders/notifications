package com.fri.rso.fririders.notifications.controller;

import com.fri.rso.fririders.notifications.service.MailClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "v1/notifications")
public class NotificationsController {

    @Autowired
    MailClient mailClient;

    @PostMapping("/mail")
    public ResponseEntity<String> notifyDummy(
            @RequestParam final String recipient,
            @RequestParam final String subject,
            @RequestParam final String message) {

        try {
            mailClient.prepareAndSend(recipient, subject, message);
        } catch (MailException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Error sending e-mail. \n" + e.toString());
        }
        return ResponseEntity.ok(String.format("Notified %s: '%s'.", recipient, message));
    }
}
