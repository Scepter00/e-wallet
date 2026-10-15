package application.ewallet.infrastructure.adapters.output.mappers;

import application.ewallet.domain.models.UserIdentity;
import javax.annotation.processing.Generated;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-14T13:51:32+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Microsoft)"
)
@Component
public class KeycloakMapperImpl implements KeycloakMapper {

    @Override
    public UserRepresentation mapUserIdentityToUserRepresentation(UserIdentity userIdentity) {
        if ( userIdentity == null ) {
            return null;
        }

        UserRepresentation userRepresentation = new UserRepresentation();

        userRepresentation.setUsername( userIdentity.getEmail() );
        userRepresentation.setId( userIdentity.getId() );
        userRepresentation.setFirstName( userIdentity.getFirstName() );
        userRepresentation.setLastName( userIdentity.getLastName() );
        userRepresentation.setEmail( userIdentity.getEmail() );
        userRepresentation.setEnabled( userIdentity.isEnabled() );
        userRepresentation.setEmailVerified( userIdentity.isEmailVerified() );

        return userRepresentation;
    }

    @Override
    public RoleRepresentation mapUserIdentityToRoleRepresentation(UserIdentity userIdentity) {
        if ( userIdentity == null ) {
            return null;
        }

        RoleRepresentation roleRepresentation = new RoleRepresentation();

        roleRepresentation.setId( userIdentity.getId() );

        return roleRepresentation;
    }

    @Override
    public UserIdentity mapUserRepresentationToUserIdentity(UserRepresentation userRepresentation) {
        if ( userRepresentation == null ) {
            return null;
        }

        UserIdentity.UserIdentityBuilder userIdentity = UserIdentity.builder();

        userIdentity.email( userRepresentation.getUsername() );
        userIdentity.id( userRepresentation.getId() );
        userIdentity.firstName( userRepresentation.getFirstName() );
        userIdentity.lastName( userRepresentation.getLastName() );
        if ( userRepresentation.isEnabled() != null ) {
            userIdentity.enabled( userRepresentation.isEnabled() );
        }
        if ( userRepresentation.isEmailVerified() != null ) {
            userIdentity.emailVerified( userRepresentation.isEmailVerified() );
        }

        return userIdentity.build();
    }
}
