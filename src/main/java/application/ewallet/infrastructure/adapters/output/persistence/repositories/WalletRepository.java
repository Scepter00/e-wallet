package application.ewallet.infrastructure.adapters.output.persistence.repositories;

import application.ewallet.infrastructure.adapters.output.persistence.entities.WalletEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WalletRepository extends MongoRepository<WalletEntity, String> {
    Optional<WalletEntity> findByUserId(String userId);

    boolean existsByUserId(String userId);
}
