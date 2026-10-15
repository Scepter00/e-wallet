package application.ewallet.infrastructure.adapters.output.persistence.mappers;

import application.ewallet.domain.models.UserIdentity;
import application.ewallet.infrastructure.adapters.output.persistence.entities.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-14T13:51:32+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Microsoft)"
)
@Component
public class UserIdentityMapperImpl implements UserIdentityMapper {

    @Override
    public UserEntity mapUserIdentityToUserEntity(UserIdentity userIdentity) {
        if ( userIdentity == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.id( userIdentity.getId() );
        userEntity.email( userIdentity.getEmail() );
        userEntity.firstName( userIdentity.getFirstName() );
        userEntity.lastName( userIdentity.getLastName() );
        userEntity.phoneNumber( userIdentity.getPhoneNumber() );
        userEntity.userRole( userIdentity.getUserRole() );
        userEntity.enabled( userIdentity.isEnabled() );
        userEntity.emailVerified( userIdentity.isEmailVerified() );
        userEntity.password( userIdentity.getPassword() );
        userEntity.createdAt( userIdentity.getCreatedAt() );

        return userEntity.build();
    }

    @Override
    public UserIdentity mapUserEntityToUserIdentity(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserIdentity.UserIdentityBuilder userIdentity = UserIdentity.builder();

        userIdentity.id( userEntity.getId() );
        userIdentity.email( userEntity.getEmail() );
        userIdentity.firstName( userEntity.getFirstName() );
        userIdentity.lastName( userEntity.getLastName() );
        userIdentity.phoneNumber( userEntity.getPhoneNumber() );
        userIdentity.userRole( userEntity.getUserRole() );
        userIdentity.enabled( userEntity.isEnabled() );
        userIdentity.emailVerified( userEntity.isEmailVerified() );
        userIdentity.password( userEntity.getPassword() );
        userIdentity.createdAt( userEntity.getCreatedAt() );

        return userIdentity.build();
    }
}
