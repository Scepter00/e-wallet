package application.ewallet.infrastructure.adapters.input.mappers;

import application.ewallet.domain.models.UserIdentity;
import application.ewallet.infrastructure.adapters.input.data.request.UserIdentityRequest;
import application.ewallet.infrastructure.adapters.input.data.responses.UserIdentityResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-14T13:51:32+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Microsoft)"
)
@Component
public class IdentityMapperImpl implements IdentityMapper {

    @Override
    public UserIdentity mapUserIdentityRequestToUserIdentity(UserIdentityRequest userIdentityRequest) {
        if ( userIdentityRequest == null ) {
            return null;
        }

        UserIdentity.UserIdentityBuilder userIdentity = UserIdentity.builder();

        userIdentity.id( userIdentityRequest.getId() );
        userIdentity.email( userIdentityRequest.getEmail() );
        userIdentity.firstName( userIdentityRequest.getFirstName() );
        userIdentity.lastName( userIdentityRequest.getLastName() );
        userIdentity.userRole( userIdentityRequest.getUserRole() );

        return userIdentity.build();
    }

    @Override
    public UserIdentityResponse mapUserIdentityResponseToUserIdentity(UserIdentity userIdentity) {
        if ( userIdentity == null ) {
            return null;
        }

        UserIdentityResponse userIdentityResponse = new UserIdentityResponse();

        userIdentityResponse.setId( userIdentity.getId() );

        return userIdentityResponse;
    }
}
