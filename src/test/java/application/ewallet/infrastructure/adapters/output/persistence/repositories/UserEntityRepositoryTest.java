package application.ewallet.infrastructure.adapters.output.persistence.repositories;

import application.ewallet.domain.enums.UserRole;
import application.ewallet.infrastructure.adapters.output.persistence.entities.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataMongoTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=4.0.2")
class UserEntityRepositoryTest {

    @Autowired
    private UserEntityRepository userEntityRepository;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder()
                .id("3dc5ab57-1da0-4567-82fa-073cc05af4ce")
                .firstName("John")
                .lastName("Doe")
                .email("doe@gmail.com")
                .phoneNumber("08012345678")
                .password("Password@123")
                .userRole(UserRole.CUSTOMER)
                .createdAt(LocalDateTime.now().toString())
                .build();
    }

    @Test
    void saveUser() {
        userEntity = userEntityRepository.save(userEntity);

        assertNotNull(userEntity);
        log.info("Saved user: ===========> {}", userEntity);
    }

    @Test
    void findUser () {
        userEntity = userEntityRepository.findById(userEntity.getId()).get();

        assertNotNull(userEntity);
        log.info("User found:============> {}", userEntity);
    }
}