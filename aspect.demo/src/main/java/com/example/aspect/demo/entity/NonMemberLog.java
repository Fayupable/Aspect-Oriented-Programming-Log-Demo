package com.example.aspect.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "non_member_log")
public class NonMemberLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "method", nullable = false)
    private String method;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "ip_address", nullable = false)
    private String ip;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "response")
    private String response;

    @Column(name = "status")
    private String status;

    @Column(name = "request")
    private String request;

    @Column(name = "exception")
    private String exception;

    @Column(name = "execution_time")
    private long executionTime;  // Time in milliseconds

    @Column(name = "login_time", nullable = false)
    private LocalDateTime loginTime;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public NonMemberLog(String action, String method, String path, String ip, String userAgent, String response, String status, String request, String exception, long executionTime, LocalDateTime loginTime, LocalDateTime createdAt) {
        this.action = action;
        this.method = method;
        this.path = path;
        this.ip = ip;
        this.userAgent = userAgent;
        this.response = response;
        this.status = status;
        this.request = request;
        this.exception = exception;
        this.executionTime = executionTime;
        this.loginTime = loginTime;
        this.createdAt = createdAt;
    }

    public NonMemberLog(String s, String method, String path, String ip, String userAgent, Object o, String status, String requestBody, String message, long i) {
        this.action = s;
        this.method = method;
        this.path = path;
        this.ip = ip;
        this.userAgent = userAgent;
        this.response = o.toString();
        this.status = status;
        this.request = requestBody;
        this.exception = message;
        this.executionTime = i;
    }
}
