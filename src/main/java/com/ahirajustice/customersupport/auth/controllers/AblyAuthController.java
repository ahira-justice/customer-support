package com.ahirajustice.customersupport.auth.controllers;

import com.ahirajustice.customersupport.auth.services.AblyAuthService;
import com.ahirajustice.customersupport.common.error.ErrorResponse;
import io.ably.lib.rest.Auth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Ably Auth")
@RestController
@RequestMapping("api/ably/auth")
@RequiredArgsConstructor
public class AblyAuthController {

    private final AblyAuthService ablyAuthService;

    @Operation(summary = "Get Ably Token Request", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Auth.TokenRequest.class)) }),
                    @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            }
    )
    @RequestMapping(path = "/token-request", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Auth.TokenRequest getAblyTokenRequest() {
        return ablyAuthService.getAblyTokenRequest();
    }

}
