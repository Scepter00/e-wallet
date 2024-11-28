package application.ewallet.application.output.Identity;

import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.UserIdentity;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface IdentityManagerOutputPort {

    UserIdentity createUser(UserIdentity userIdentity) throws WalletException;

    UserRepresentation getUserRepresentation(UserIdentity userIdentity, Boolean exactMatch) throws WalletException;

    UserIdentity login(UserIdentity userIdentity) throws WalletException;

    UserIdentity findUser(UserIdentity userIdentity) throws WalletException;

    void deleteUser(UserIdentity userIdentity) throws WalletException;
}
