package com.example.aspect.demo.controller;

import com.example.aspect.demo.entity.Person;
import com.example.aspect.demo.service.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {
    private final PersonService personService;

    @PostMapping("/login/member")
    public ResponseEntity<String> memberLogin(@RequestParam Long personId, HttpServletRequest request) {
        // Simulate checking if the person exists
        Person person = personService.findPersonById(personId);
        if (person == null) {
            return ResponseEntity.status(404).body("Person not found");
        }

        return ResponseEntity.ok("Login successful for member: " + person.getName());
    }

    @PostMapping("/login/non-member")
    public ResponseEntity<String> nonMemberLogin(HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();

        return ResponseEntity.ok("Login attempt by non-member from IP: " + ipAddress);
    }
}
