package application.ewallet.infrastructure.adapters.output.persistence.mappers;

import application.ewallet.domain.models.UserIdentity;
import application.ewallet.infrastructure.adapters.output.persistence.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserIdentityMapper {

    UserEntity mapUserIdentityToUserEntity (UserIdentity userIdentity);

    UserIdentity mapUserEntityToUserIdentity (UserEntity userEntity);
}
