package application.ewallet.infrastructure.adapters.output.persistence.repositories;

import application.ewallet.infrastructure.adapters.output.persistence.entities.PremblyEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PremblyRepository extends MongoRepository<PremblyEntity, String> {
}
