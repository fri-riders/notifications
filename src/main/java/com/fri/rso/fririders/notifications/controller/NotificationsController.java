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

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public ResponseEntity<String> friRidersInfo() {
        String data = "{" +
                "\"clani\": [\"ts4293\", \"ub6189\", \"je1468\"]," +
                "\"opis_projekta\": \"Nas projekt implementira aplikacijo za oddajo nepremicnin.\"," +
                "\"mikrostoritve\": " +
                "[\"http://169.51.16.54:30735/v1/users\"," +
                " \"http://169.51.16.54:31558/v1/bookings\"," +
                " \"http://169.51.16.54:31062/v1/accommodations\"]," +
                " \"http://169.51.16.54:32353/notifications/info\"]," +
                "\"github\": " +
                "[\"https://github.com/fri-riders/users\"," +
                " \"https://github.com/fri-riders/accommodations\"," +
                " \"https://github.com/fri-riders/display-bookings\"," +
                " \"https://github.com/fri-riders/notifications\"]," +
                "\"travis\": " +
                "[\"https://travis-ci.org/fri-riders/users\"," +
                " \"https://travis-ci.org/fri-riders/accommodations\"," +
                " \"https://travis-ci.org/fri-riders/display-bookings\"," +
                " \"https://travis-ci.org/fri-riders/notifications\"]," +
                "\"dockerhub\":" +
                " [\"https://hub.docker.com/r/tomisebjanic/rso-users\"," +
                " \"https://hub.docker.com/r/janerz6/accommodations\"," +
                " \"https://hub.docker.com/r/urosbajc/display-bookings\"," +
                " \"https://hub.docker.com/r/janerz6/notifications\"]" +
                "}";
        return ResponseEntity.ok(data);
    }
}
