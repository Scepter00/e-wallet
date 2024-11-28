package application.ewallet.infrastructure.adapters.output.persistence.repositories;

import application.ewallet.infrastructure.adapters.output.persistence.entities.TransactionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<TransactionEntity, String> {
}
