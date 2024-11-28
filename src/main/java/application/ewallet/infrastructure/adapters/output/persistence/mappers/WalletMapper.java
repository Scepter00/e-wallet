package application.ewallet.infrastructure.adapters.output.persistence.mappers;

import application.ewallet.domain.models.WalletIdentity;
import application.ewallet.infrastructure.adapters.output.persistence.entities.WalletEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    WalletEntity mapWalletIdentityToWalletEntity(WalletIdentity walletIdentity);

    WalletIdentity mapWalletEntityToWalletIdentity(WalletEntity walletEntity);
}
