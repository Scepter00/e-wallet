package application.ewallet.infrastructure.adapters.output.persistence;

import application.ewallet.application.output.Identity.UserIdentityOutputPort;
import application.ewallet.domain.exceptions.IdentityException;
import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.UserIdentity;
import application.ewallet.domain.validations.UserIdentityValidator;
import application.ewallet.domain.validations.WalletValidator;
import application.ewallet.infrastructure.adapters.output.persistence.entities.UserEntity;
import application.ewallet.infrastructure.adapters.output.persistence.mappers.UserIdentityMapper;
import application.ewallet.infrastructure.adapters.output.persistence.repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static application.ewallet.domain.enums.constants.IdentityMessages.USER_NOT_FOUND;
import static application.ewallet.domain.enums.constants.WalletMessages.EMAIL_NOT_FOUND;
import static application.ewallet.domain.validations.WalletValidator.validateEmail;

@Service
@RequiredArgsConstructor
public class UserIdentityAdapter implements UserIdentityOutputPort {
    private final UserEntityRepository userEntityRepository;
    private final UserIdentityMapper userIdentityMapper;

    @Override
    public UserIdentity save(UserIdentity userIdentity) throws WalletException {
        UserIdentityValidator.validateUserIdentity(userIdentity);
        UserEntity userEntity = userIdentityMapper.mapUserIdentityToUserEntity(userIdentity);
        userEntity = userEntityRepository.save(userEntity);
        return userIdentityMapper.mapUserEntityToUserIdentity(userEntity);
    }

    @Override
    public UserIdentity findByEmail(String email) throws WalletException {
        validateEmail(email);
        UserEntity userEntity = getUserEntityByEmail(email);
        return userIdentityMapper.mapUserEntityToUserIdentity(userEntity);
    }

    @Override
    public UserIdentity findById(String id) throws WalletException {
        WalletValidator.validateDataElement(id);
        UserEntity userEntity = userEntityRepository.findById(id).orElseThrow(() -> new IdentityException(USER_NOT_FOUND.getMessage()));
        return userIdentityMapper.mapUserEntityToUserIdentity(userEntity);
    }

    @Override
    public void deleteUserById(String id) throws WalletException {
        WalletValidator.validateDataElement(id);
        UserEntity userEntity = userEntityRepository.findById(id).orElseThrow(() -> new IdentityException(USER_NOT_FOUND.getMessage()));
        userEntityRepository.delete(userEntity);
    }

    @Override
    public void deleteUserByEmail(String email) throws WalletException {
        validateEmail(email);
        UserEntity userEntity = getUserEntityByEmail(email);
        userEntityRepository.delete(userEntity);
    }

    private UserEntity getUserEntityByEmail(String email) throws IdentityException {
        return userEntityRepository.findByEmail(email).orElseThrow(()-> new IdentityException(EMAIL_NOT_FOUND.getMessage()));
    }
}
