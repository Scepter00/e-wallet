package application.ewallet.infrastructure.adapters.input.mappers;

import application.ewallet.domain.models.UserIdentity;
import application.ewallet.infrastructure.adapters.input.data.request.UserIdentityRequest;
import application.ewallet.infrastructure.adapters.input.data.responses.UserIdentityResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IdentityMapper {

    @Mapping(target = "password", ignore = true)
    UserIdentity mapUserIdentityRequestToUserIdentity (UserIdentityRequest userIdentityRequest);

    UserIdentityResponse mapUserIdentityResponseToUserIdentity(UserIdentity userIdentity);
}
