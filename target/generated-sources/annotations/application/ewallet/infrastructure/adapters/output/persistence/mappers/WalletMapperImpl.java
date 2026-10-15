package application.ewallet.infrastructure.adapters.output.persistence.mappers;

import application.ewallet.domain.models.WalletIdentity;
import application.ewallet.infrastructure.adapters.output.persistence.entities.WalletEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-14T13:51:32+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Microsoft)"
)
@Component
public class WalletMapperImpl implements WalletMapper {

    @Override
    public WalletEntity mapWalletIdentityToWalletEntity(WalletIdentity walletIdentity) {
        if ( walletIdentity == null ) {
            return null;
        }

        WalletEntity.WalletEntityBuilder walletEntity = WalletEntity.builder();

        walletEntity.id( walletIdentity.getId() );
        walletEntity.userId( walletIdentity.getUserId() );
        walletEntity.balance( walletIdentity.getBalance() );
        walletEntity.createdAt( walletIdentity.getCreatedAt() );
        walletEntity.updatedAt( walletIdentity.getUpdatedAt() );
        walletEntity.createdBy( walletIdentity.getCreatedBy() );
        walletEntity.walletStatus( walletIdentity.getWalletStatus() );

        return walletEntity.build();
    }

    @Override
    public WalletIdentity mapWalletEntityToWalletIdentity(WalletEntity walletEntity) {
        if ( walletEntity == null ) {
            return null;
        }

        WalletIdentity.WalletIdentityBuilder walletIdentity = WalletIdentity.builder();

        walletIdentity.id( walletEntity.getId() );
        walletIdentity.userId( walletEntity.getUserId() );
        walletIdentity.balance( walletEntity.getBalance() );
        walletIdentity.createdAt( walletEntity.getCreatedAt() );
        walletIdentity.updatedAt( walletEntity.getUpdatedAt() );
        walletIdentity.createdBy( walletEntity.getCreatedBy() );
        walletIdentity.walletStatus( walletEntity.getWalletStatus() );

        return walletIdentity.build();
    }
}
