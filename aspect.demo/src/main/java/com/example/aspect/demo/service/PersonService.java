package com.example.aspect.demo.service;

import com.example.aspect.demo.entity.Person;
import com.example.aspect.demo.repository.IPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final IPersonRepository personRepository;

    public Person findPersonById(Long personId) {
        return personRepository.findById(personId).orElse(null);
    }
}
