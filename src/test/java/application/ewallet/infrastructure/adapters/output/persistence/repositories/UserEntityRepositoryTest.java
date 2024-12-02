package application.ewallet.infrastructure.adapters.output.persistence.repositories;

import application.ewallet.domain.models.UserIdentity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=4.0.2")
class UserEntityRepositoryTest {

    @Autowired
    private UserEntityRepository userEntityRepository;

    private UserIdentity userIdentity;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByEmail() {}
}