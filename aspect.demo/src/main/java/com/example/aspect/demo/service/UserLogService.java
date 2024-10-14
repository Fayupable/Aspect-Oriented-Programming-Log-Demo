package com.example.aspect.demo.service;

import com.example.aspect.demo.entity.UserLog;
import com.example.aspect.demo.repository.IUserLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLogService {
    private final IUserLogRepository userLogRepository;

    public void saveLog(UserLog log) {
        userLogRepository.save(log);
    }

}
