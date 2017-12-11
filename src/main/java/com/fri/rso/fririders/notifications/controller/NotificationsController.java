package com.fri.rso.fririders.notifications.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "v1/notifications")
public class NotificationsController {

    @GetMapping
    public ResponseEntity<String> notifyDummy(){
        return ResponseEntity.ok("Notified");
    }
}
