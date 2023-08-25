package com.estrella.PicoPlaca.controllers;

import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.estrella.PicoPlaca.services.PicoYPlacaService;

import org.springframework.ui.Model;

@RestController
@RequestMapping("/api")
public class PicoYPlacaController {
 
    @Autowired
    private PicoYPlacaService service;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Check if a vehicle can drive on a given date and time
    @GetMapping("/canDrive")
    public ResponseEntity<String> canDrive(
            @RequestParam String plate,
            @RequestParam String date,
            @RequestParam String time,
            Model model) {
        try {
            String message = service.canDrive(plate, date, time);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (ParseException e) {
            return new ResponseEntity<>("Error in parsing date or time.", HttpStatus.BAD_REQUEST);
        }
    }

}