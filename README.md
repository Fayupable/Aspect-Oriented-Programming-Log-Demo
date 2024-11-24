# Aspect Demo Project

[![Java](https://img.shields.io/badge/Java-17-red)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0.0-green)](https://spring.io/projects/spring-boot)

## Table of Contents

- [Project Overview](#project-overview)
- [Project Structure](#project-structure)
- [Technical Stack](#technical-stack)
- [Service Implementation](#service-implementation)
- [Repository Implementation](#repository-implementation)
- [Contributing](#contributing)
- [Future Improvements](#future-improvements)

## Project Overview

This project demonstrates the use of Spring AOP (Aspect-Oriented Programming) in a Spring Boot application. It includes services and repositories for managing user and non-member logs.

## Project Structure

```markdown
aspect-demo/
│
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com/example/aspect/demo/
│ │ │     ├── entity/ # Entity classes
│ │ │     ├── repository/ # Repository interfaces
│ │ │     ├── service/ # Service classes
│ │ │     └── aspect/ # Aspect classes
│ │ └── resources/ # Application configuration
├── README.md # Documentation
└── .idea/ # IDE configuration files
```

## Technical Stack

### Core Technologies

- **Language**: Java 17
- **Framework**: Spring Boot 3.0.0
- **Dependency Management**: Maven

### Key Libraries

- Spring Data JPA
- Lombok
- Slf4j Logging

## Service Implementation

### NonMemberLogService

```java
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
```

### UserLogService

```java
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
```

## Repository Implementation

### INonMemberLogRepository

```java
package com.example.aspect.demo.repository;

import com.example.aspect.demo.entity.NonMemberLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INonMemberLogRepository extends JpaRepository<NonMemberLog, Long> {
}
```

### IUserLogRepository

```java
package com.example.aspect.demo.repository;

import com.example.aspect.demo.entity.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserLogRepository extends JpaRepository<UserLog, Long> {
}
```

## Contributing

1. Fork the repository
2. Create a new branch (`git checkout -b feature-branch`)
3. Commit your changes (`git commit -m 'Add some feature'`)
4. Push to the branch (`git push origin feature-branch`)
5. Open a pull request

## Future Improvements

- Implement more aspects for logging and security
- Add unit and integration tests
- Enhance error handling and validation mechanisms

---

**Feel free to explore, run the services, and experiment with Spring AOP in this demo project!**
