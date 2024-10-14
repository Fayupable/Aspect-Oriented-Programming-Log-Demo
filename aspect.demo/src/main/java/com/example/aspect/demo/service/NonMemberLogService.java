package com.example.aspect.demo.service;

import com.example.aspect.demo.entity.NonMemberLog;
import com.example.aspect.demo.repository.INonMemberLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NonMemberLogService {
    private final INonMemberLogRepository nonMemberLogRepository;

    public void saveLog(NonMemberLog log) {
        nonMemberLogRepository.save(log);
    }
}
