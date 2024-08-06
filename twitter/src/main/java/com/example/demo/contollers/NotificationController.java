package com.example.demo.contollers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.demo.models.Notes;
import com.example.demo.services.NotificationService;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    public Optional<Notes> findNote(int id) {
        return notificationService.getNotes(id);
    }

}
