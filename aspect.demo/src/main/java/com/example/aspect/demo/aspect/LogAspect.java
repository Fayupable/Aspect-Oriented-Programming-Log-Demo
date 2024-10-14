package com.example.aspect.demo.aspect;

import com.example.aspect.demo.entity.NonMemberLog;
import com.example.aspect.demo.entity.Person;
import com.example.aspect.demo.entity.UserLog;
import com.example.aspect.demo.service.NonMemberLogService;
import com.example.aspect.demo.service.PersonService;
import com.example.aspect.demo.service.UserLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {
    private final UserLogService userLogService;
    private final NonMemberLogService nonMemberLogService;
    private final PersonService personService;
    private final HttpServletRequest request;

    @Around("execution(* com.example.aspect.demo.controller.*.*(..))")
    public Object logExecutionDetails(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            logException(joinPoint, e);
            throw e;
        }

        long executionTime = System.currentTimeMillis() - startTime;

        String methodName = joinPoint.getSignature().getName();
        String path = request.getRequestURI();
        String method = request.getMethod();
        String ip = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        String status = result != null ? "Success" : "Failed";
        String requestBody = getRequestPayload(joinPoint.getArgs());

        if (isMemberLogin(joinPoint)) {
            Object[] args = joinPoint.getArgs();
            Long personId = null;

            if (args.length > 0 && args[0] instanceof Long) {
                personId = (Long) args[0];
            }

            if (personId != null) {
                Person person = personService.findPersonById(personId);
                if (person != null) {
                    logUserLog(person, methodName, path, method, ip, userAgent, executionTime, requestBody, status);
                } else {
                    logNonMemberLog(methodName, path, method, ip, userAgent, executionTime, requestBody, status);
                }
            } else {
                logNonMemberLog(methodName, path, method, ip, userAgent, executionTime, requestBody, status);
            }
        } else {
            logNonMemberLog(methodName, path, method, ip, userAgent, executionTime, requestBody, status);
        }

        return result;
    }

    private void logUserLog(Person person, String methodName, String path, String method, String ip,
                            String userAgent, long executionTime, String requestBody, String status) {
        UserLog userLog = new UserLog(
                person,
                "Login",
                method,
                path,
                ip,
                userAgent,
                "Response data",
                status,
                requestBody,
                null,
                executionTime
        );
        userLog.setLoginTime(LocalDateTime.now());
        userLogService.saveLog(userLog);
    }

    private void logNonMemberLog(String methodName, String path, String method, String ip, String userAgent,
                                 long executionTime, String requestBody, String status) {
        NonMemberLog nonMemberLog = new NonMemberLog(
                "Non-member Login",
                method,
                path,
                ip,
                userAgent,
                "Response data",
                status,
                requestBody,
                null,
                executionTime
        );
        nonMemberLog.setLoginTime(LocalDateTime.now());
        nonMemberLogService.saveLog(nonMemberLog);
    }

    private void logException(ProceedingJoinPoint joinPoint, Exception e) {
        String methodName = joinPoint.getSignature().getName();
        String path = request.getRequestURI();
        String method = request.getMethod();
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String requestBody = getRequestPayload(joinPoint.getArgs());
        String status = "Failed";

        if (isMemberLogin(joinPoint)) {
            Object[] args = joinPoint.getArgs();
            Long personId = args.length > 0 && args[0] instanceof Long ? (Long) args[0] : null;
            Person person = personId != null ? personService.findPersonById(personId) : null;

            UserLog userLog = new UserLog(
                    person,
                    "Login",
                    method,
                    path,
                    ip,
                    userAgent,
                    null,
                    status,
                    requestBody,
                    e.getMessage(),
                    0
            );
            userLog.setLoginTime(LocalDateTime.now());
            userLogService.saveLog(userLog);
        } else {
            NonMemberLog nonMemberLog = new NonMemberLog(
                    "Non-member Login",
                    method,
                    path,
                    ip,
                    userAgent,
                    null,
                    status,
                    requestBody,
                    e.getMessage(),
                    0
            );
            nonMemberLog.setLoginTime(LocalDateTime.now());
            nonMemberLogService.saveLog(nonMemberLog);
        }
    }
    private String getClientIpAddress(HttpServletRequest request) {
        String[] headersToCheck = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED",
                "HTTP_X_CLUSTER_CLIENT_IP",
                "HTTP_CLIENT_IP",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED",
                "HTTP_VIA",
                "REMOTE_ADDR"
        };

        for (String header : headersToCheck) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip.contains(",") ? ip.split(",")[0] : ip;
            }
        }

        return request.getRemoteAddr();
    }

    private boolean isMemberLogin(ProceedingJoinPoint joinPoint) {
        return joinPoint.getSignature().getName().contains("memberLogin");
    }

    private String getRequestPayload(Object[] args) {
        return args != null && args.length > 0 ? Arrays.toString(args) : "No Payload";
    }
}