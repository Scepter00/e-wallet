package application.ewallet.infrastructure.adapters.output.identityManager;

import application.ewallet.application.output.Identity.IdentityManagerOutputPort;
import application.ewallet.domain.enums.constants.WalletMessages;
import application.ewallet.domain.exceptions.IdentityException;
import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.UserIdentity;
import application.ewallet.domain.validations.WalletValidator;
import application.ewallet.infrastructure.adapters.output.mappers.KeycloakMapper;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static application.ewallet.domain.enums.constants.IdentityMessages.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakAdapter implements IdentityManagerOutputPort {
    private final Keycloak keycloak;
    private final KeycloakMapper keycloakMapper;

    @Value("${realm}")
    private String KEYCLOAK_REALM;

    @Value("${keycloak.clientId}")
    private String CLIENT_ID;

    @Value("${keycloak.server.url}")
    private String SERVER_URL;

    @Value("${keycloak.client.secret}")
    private String CLIENT_SECRET;

    @Override
    public UserIdentity createUser(UserIdentity userIdentity) throws WalletException {
        WalletValidator.validateDataInput(userIdentity);
        UserRepresentation userRepresentation = keycloakMapper.mapUserIdentityToUserRepresentation(userIdentity);
        setUpPassword(userIdentity.getPassword(), userRepresentation);
        userRepresentation.setEmailVerified(Boolean.TRUE);
        userRepresentation.setEnabled(Boolean.TRUE);
        createUserRepresentation(userRepresentation, userIdentity);
        return userIdentity;
    }

    public RoleRepresentation getRoleRepresentation(UserIdentity userIdentity) throws WalletException {
        if (userIdentity.getUserRole() == null || StringUtils.isEmpty(userIdentity.getUserRole().name()))
            throw new IdentityException(INVALID_VALID_ROLE.getMessage());
        RoleRepresentation roleRepresentation;
        try {
            roleRepresentation = keycloak
                    .realm(KEYCLOAK_REALM)
                    .roles()
                    .get(userIdentity.getUserRole().name().toUpperCase().trim())
                    .toRepresentation();
        }catch (NotFoundException exception){
            throw new IdentityException("Not Found: Role with name "+ userIdentity.getUserRole());
        }
        return roleRepresentation;
    }

    @Override
    public UserRepresentation getUserRepresentation(UserIdentity userIdentity, Boolean exactMatch) throws WalletException {
        validateUserIdentity(userIdentity);
        WalletValidator.validateEmail(userIdentity.getEmail());
        return keycloak
                .realm(KEYCLOAK_REALM)
                .users()
                .search(userIdentity.getEmail(), exactMatch)
                .stream().findFirst().orElseThrow(()-> new IdentityException(USER_NOT_FOUND.getMessage()));
    }

    public UserResource getUserResource(UserIdentity userIdentity) throws WalletException {
        validateUserIdentityDetails(userIdentity);
        WalletValidator.validateDataInput(userIdentity);
        return keycloak
                .realm(KEYCLOAK_REALM)
                .users()
                .get(userIdentity.getId());
    }

private void createUserRepresentation(UserRepresentation userRepresentation, UserIdentity userIdentity) throws IdentityException {
    try {
        UsersResource users = keycloak.realm(KEYCLOAK_REALM).users();
        Response response = users.create(userRepresentation);
        if (response.getStatusInfo().equals(Response.Status.CONFLICT)) {
            throw new IdentityException(USER_IDENTITY_ALREADY_EXISTS.getMessage());
        }
        UserRepresentation createdUserRepresentation = getUserRepresentation(userIdentity, Boolean.TRUE);
        userIdentity.setId(createdUserRepresentation.getId());
        assignRole(userIdentity);
    } catch (NotFoundException | WalletException exception) {
        throw new IdentityException(exception.getMessage());
    }
}

    @Override
    public UserIdentity login(UserIdentity userIdentity) throws WalletException {
        WalletValidator.validateDataElement(userIdentity.getEmail());
        WalletValidator.validateDataElement(userIdentity.getPassword());
        UserIdentity user = findUser(userIdentity);
        log.info("Attempting login for user: ==========================> {}, {}", userIdentity.getEmail(), userIdentity.getPassword());
        try {
            Keycloak keycloakClient = getKeycloak(userIdentity);
            TokenManager tokenManager = keycloakClient.tokenManager();
            user.setAccessToken(tokenManager.getAccessToken().getToken());
            user.setRefreshToken(tokenManager.refreshToken().getRefreshToken());
            log.info("Token {}", user);
            return user;
        } catch (NotAuthorizedException | BadRequestException exception) {
            log.error("Error logging in user: {}", exception.getMessage());
            throw new WalletException(WalletMessages.INVALID_PASSWORD_OR_PASSWORD.getMessage());
        }
    }

    @Override
    public UserIdentity findUser(UserIdentity userIdentity) throws WalletException {
        UserRepresentation foundUser = getUserRepresentation(userIdentity, Boolean.TRUE);
        return keycloakMapper.mapUserRepresentationToUserIdentity(foundUser);
    }

    @Override
    public void deleteUser(UserIdentity userIdentity) throws WalletException {
        validateUserIdentity(userIdentity);
        UserResource userResource = getUserResource(userIdentity);
        try{
            userResource.remove();
        }catch (NotFoundException exception) {
            log.info("deleteUser called with invalid user id: {}", userIdentity.getId());
            throw new WalletException("User does not exist");
        }
    }

    private void setUpPassword(String password, UserRepresentation keyCloakUser) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        credentialRepresentation.setTemporary(false);
        keyCloakUser.setCredentials(Collections.singletonList(credentialRepresentation));
    }

    private Keycloak getKeycloak(UserIdentity userIdentity) {
        String email = userIdentity.getEmail().toLowerCase().trim();
        String password = userIdentity.getPassword();
        return KeycloakBuilder.builder()
                .grantType(OAuth2Constants.PASSWORD)
                .realm(KEYCLOAK_REALM)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .username(email)
                .password(password)
                .serverUrl(SERVER_URL)
                .build();
    }


    private void assignRole(UserIdentity userIdentity) throws WalletException {
        try {
            RoleRepresentation roleRepresentation = getRoleRepresentation(userIdentity);
            UserResource userResource = getUserResource(userIdentity);
            userResource.roles().realmLevel().add(List.of(roleRepresentation));
        } catch (NotFoundException | IdentityException exception) {
            throw new IdentityException(String.format("Resource not found: %s", exception.getMessage()));
        }
    }

    private void validateUserIdentity(UserIdentity userIdentity) throws WalletException {
        if (userIdentity == null) {
            throw new WalletException(USER_NOT_FOUND.getMessage());
        }
    }

    private void validateUserIdentityDetails(UserIdentity userIdentity) throws WalletException {
        if (userIdentity == null) {
            throw new WalletException(USER_NOT_FOUND.getMessage());
        }
        if (StringUtils.isEmpty(userIdentity.getEmail())
                || StringUtils.isEmpty(userIdentity.getId())
                || StringUtils.isEmpty(userIdentity.getFirstName())
                || StringUtils.isEmpty(userIdentity.getLastName())
                || userIdentity.getUserRole() == null
                || StringUtils.isEmpty(userIdentity.getUserRole().name()))
            throw new IdentityException(INVALID_REGISTRATION_DETAILS.getMessage());
        getRoleRepresentation(userIdentity);
    }
}
