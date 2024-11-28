package application.ewallet.infrastructure.adapters.output.persistence.repositories;

import application.ewallet.infrastructure.adapters.output.persistence.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserEntityRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
