package application.ewallet.infrastructure.adapters.output.persistence;

import application.ewallet.application.output.wallet.WalletManagerOutputPort;
import application.ewallet.domain.enums.constants.WalletMessages;
import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.WalletIdentity;
import application.ewallet.domain.validations.WalletValidator;
import application.ewallet.infrastructure.adapters.output.persistence.entities.WalletEntity;
import application.ewallet.infrastructure.adapters.output.persistence.mappers.WalletMapper;
import application.ewallet.infrastructure.adapters.output.persistence.repositories.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletAdapter implements WalletManagerOutputPort {
    private final WalletMapper walletMapper;
    private final WalletRepository walletRepository;

    @Override
    public WalletIdentity save(WalletIdentity walletIdentity) throws WalletException {
        WalletValidator.validateUserId(walletIdentity.getUserId());
        //WalletValidator.validateWalletId(walletIdentity.getId());
        boolean foundWallet = walletRepository.findByUserId(walletIdentity.getUserId()).isPresent();
        if(foundWallet){
            throw new WalletException(WalletMessages.WALLET_ALREADY_EXIST.getMessage());
        }

        WalletEntity savedWalletEntity = walletMapper.mapWalletIdentityToWalletEntity(walletIdentity);
        savedWalletEntity = walletRepository.save(savedWalletEntity);
        return walletMapper.mapWalletEntityToWalletIdentity(savedWalletEntity);
    }

    public WalletIdentity findByUserId(String userId) throws WalletException {
        WalletValidator.validateUserId(userId);
        WalletEntity walletEntity = walletRepository.findByUserId(userId).orElseThrow(() -> new WalletException(WalletMessages.WALLET_NOT_FOUND.getMessage()));
        return walletMapper.mapWalletEntityToWalletIdentity(walletEntity);
    }

    @Override
    public Optional<WalletIdentity> getWalletByUserId(String userId) throws WalletException {
        WalletValidator.validateDataElement(userId);
        return walletRepository.findByUserId(userId)
                .map(walletMapper::mapWalletEntityToWalletIdentity);
    }


    @Override
    public void deleteWalletByUserId(String userId) throws WalletException {
        WalletValidator.validateUserId(userId);
        WalletEntity walletEntity = walletRepository.findByUserId(userId).orElseThrow(() -> new WalletException(WalletMessages.WALLET_NOT_FOUND.getMessage()));
        walletRepository.delete(walletEntity);
    }
}
