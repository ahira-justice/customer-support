package com.ahirajustice.customersupport.role.controllers;

import com.ahirajustice.customersupport.common.error.ErrorResponse;
import com.ahirajustice.customersupport.common.error.ValidationErrorResponse;
import com.ahirajustice.customersupport.common.constants.AuthorityConstants;
import com.ahirajustice.customersupport.role.requests.RoleCreateRequest;
import com.ahirajustice.customersupport.role.requests.RoleUpdateRequest;
import com.ahirajustice.customersupport.role.services.RoleService;
import com.ahirajustice.customersupport.role.viewmodels.RoleViewModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.ahirajustice.customersupport.common.constants.AuthorityConstants.AUTH_PREFIX;
import static com.ahirajustice.customersupport.common.constants.AuthorityConstants.AUTH_SUFFIX;

@Tag(name = "Roles")
@RestController
@RequestMapping("api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "Get Roles", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RoleViewModel.class))) }),
            @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
        }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_VIEW_ALL_ROLES + AUTH_SUFFIX)
    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<RoleViewModel> getRoles() {
        return roleService.getRoles();
    }

    @Operation(summary = "Get Role", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RoleViewModel.class)) }),
            @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "404", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
        }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_VIEW_ROLE + AUTH_SUFFIX)
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public RoleViewModel getRole(@PathVariable long id) {
        return roleService.getRole(id);
    }

    @Operation(summary = "Create Role", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RoleViewModel.class)) }),
            @ApiResponse(responseCode = "400", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "422", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) })
        }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_CREATE_ROLE + AUTH_SUFFIX)
    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public RoleViewModel createRole(@Valid @RequestBody RoleCreateRequest request) {
        return roleService.createRole(request);
    }

    @Operation(summary = "Update Role", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RoleViewModel.class)) }),
            @ApiResponse(responseCode = "400", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "404", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "422", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) })
        }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_UPDATE_ROLE + AUTH_SUFFIX)
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public RoleViewModel updateRole(@PathVariable long id, @Valid @RequestBody RoleUpdateRequest request) {
        return roleService.updateRole(request, id);
    }

}
