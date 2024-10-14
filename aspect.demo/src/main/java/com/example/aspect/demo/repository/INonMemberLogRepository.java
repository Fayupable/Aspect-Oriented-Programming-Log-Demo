package com.example.aspect.demo.repository;

import com.example.aspect.demo.entity.NonMemberLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INonMemberLogRepository extends JpaRepository<NonMemberLog, Long> {
}
