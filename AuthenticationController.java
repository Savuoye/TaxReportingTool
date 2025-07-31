package com.fisglobal.taxreporting.controller.authentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fisglobal.taxreporting.controller.authentication.validate.ValidationErrorDto;

import de.kordoba.framework.common.log.KORLogger;


/**
 * To handle the security authentication and authorization of this application
 */
@RestController
@Tag(name = "taxReportingAuthentication", description = "Authentication")
@Validated
public class AuthenticationController {
    private static final KORLogger LOG = KORLogger.getLogger(AuthenticationController.class);

    /**
     * This method will process LDAP authentication process
     *
     * @return Authentication response
     */
    @Operation(description = "Authenticate an user", tags = { "taxReportingAuthentication" })
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ValidationErrorDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content()) })
    @PostMapping("/authentication")
    @PreAuthorize("isAuthenticated()")
    public AuthenticationResponse authenticate() {
        LOG.trace("[AuthenticationController] [authenticate] Enter into authenticate method");
        return AuthenticationFactory.getAuthenticationResponse("LDAP");
    }

    /**
     * This method will process JWT authorization process
     *
     * @return Authentication response
     */
    @Operation(description = "Authenticate jwt token", tags = { "taxReportingAuthentication" })
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ValidationErrorDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content()) })
    @PostMapping("/authenticationJWT")
    @PreAuthorize("isAuthenticated()")
    public AuthenticationResponse authenticateJWT() {
        LOG.trace("[AuthenticationController] [authenticateJWT] Enter into authenticate jwt method");
        return AuthenticationFactory.getAuthenticationResponse("JWT");
    }
}
