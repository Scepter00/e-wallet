package application.ewallet.infrastructure.adapters.input.controllers;

import application.ewallet.application.input.CreateUserUseCase;
import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.UserIdentity;
import application.ewallet.infrastructure.adapters.input.data.request.UserIdentityRequest;
import application.ewallet.infrastructure.adapters.input.data.responses.ApiResponse;
import application.ewallet.infrastructure.adapters.input.mappers.IdentityMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static application.ewallet.infrastructure.enums.ControllerConstants.RESPONSE_IS_SUCCESSFUL;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class IdentityManagerController {
    private final IdentityMapper identityMapper;
    private final CreateUserUseCase createUserUseCase;

    @PostMapping("signup/")
    public ResponseEntity<ApiResponse<?>> signup(@RequestBody @Valid UserIdentityRequest userIdentityRequest) throws WalletException {
        UserIdentity userIdentity = identityMapper.mapUserIdentityRequestToUserIdentity (userIdentityRequest);
        userIdentity = createUserUseCase.signup(userIdentity);
        return ResponseEntity.ok(ApiResponse.<UserIdentity>builder()
                .data(userIdentity)
                .message(RESPONSE_IS_SUCCESSFUL.getMessage())
                .statusCode(HttpStatus.CREATED.name())
                .build());
    }

    @GetMapping("search/user/{id}")
    public ResponseEntity<ApiResponse<?>> getUserById(@PathVariable String id) throws WalletException {
//        if (StringUtils.isBlank(id) || StringUtils.isEmpty(id))
//            return ResponseEntity.badRequest().body(ApiResponse.builder()
//                    .message(USER_ID_IS_REQUIRED.getMessage())
//                    .statusCode(HttpStatus.BAD_REQUEST.name())
//                    .build());
        UserIdentity userIdentity = createUserUseCase.findUserById(id);
        return ResponseEntity.ok(ApiResponse.<UserIdentity>builder()
                .data(userIdentity)
                .message(RESPONSE_IS_SUCCESSFUL.getMessage())
                .statusCode(HttpStatus.OK.name())
                .build());
    }

    @PostMapping("login/")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody @Valid UserIdentityRequest userIdentityRequest) throws WalletException {
        UserIdentity userIdentity = identityMapper.mapUserIdentityRequestToUserIdentity(userIdentityRequest);
        userIdentity = createUserUseCase.login(userIdentity);
        return ResponseEntity.ok(ApiResponse.<UserIdentity>builder()
                .data(userIdentity)
                .message(RESPONSE_IS_SUCCESSFUL.getMessage())
                .statusCode(HttpStatus.OK.name())
                .build());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable String id) throws WalletException {
        UserIdentity userIdentity = UserIdentity.builder().id(id).build();
        createUserUseCase.deleteUser(userIdentity);
        return ResponseEntity.ok(ApiResponse.builder()
                .message(RESPONSE_IS_SUCCESSFUL.getMessage())
                .statusCode(HttpStatus.OK.name())
                .build());
    }
}
