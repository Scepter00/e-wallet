package application.ewallet.infrastructure.adapters.output.mappers;

import application.ewallet.domain.models.UserIdentity;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface KeycloakMapper {

    @Mapping(source = "email", target = "username")
    UserRepresentation mapUserIdentityToUserRepresentation(UserIdentity userIdentity);

    RoleRepresentation mapUserIdentityToRoleRepresentation(UserIdentity userIdentity);

    @InheritInverseConfiguration
    UserIdentity mapUserRepresentationToUserIdentity(UserRepresentation userRepresentation);
}