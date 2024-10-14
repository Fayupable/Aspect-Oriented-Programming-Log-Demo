package com.example.aspect.demo.repository;

import com.example.aspect.demo.entity.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserLogRepository extends JpaRepository<UserLog, Long> {
}
