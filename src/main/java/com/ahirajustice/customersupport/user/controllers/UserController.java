package com.ahirajustice.customersupport.user.controllers;

import com.ahirajustice.customersupport.common.error.ErrorResponse;
import com.ahirajustice.customersupport.common.error.ValidationErrorResponse;
import com.ahirajustice.customersupport.common.constants.AuthorityConstants;
import com.ahirajustice.customersupport.user.queries.SearchUsersQuery;
import com.ahirajustice.customersupport.user.requests.UserCreateRequest;
import com.ahirajustice.customersupport.user.requests.UserUpdateRequest;
import com.ahirajustice.customersupport.user.services.UserService;
import com.ahirajustice.customersupport.user.viewmodels.UserViewModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.ahirajustice.customersupport.common.constants.AuthorityConstants.AUTH_PREFIX;
import static com.ahirajustice.customersupport.common.constants.AuthorityConstants.AUTH_SUFFIX;

@Tag(name = "Users")
@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Search Users", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserViewModel.class))) }),
            @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
        }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_SEARCH_USERS + AUTH_SUFFIX)
    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<UserViewModel> searchUsers(SearchUsersQuery query) {
        return userService.searchUsers(query);
    }

    @Operation(summary = "Get User", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewModel.class)) }),
            @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "404", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
        }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_VIEW_USER + AUTH_SUFFIX)
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserViewModel getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @Operation(summary = "Create User")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewModel.class)) }),
            @ApiResponse(responseCode = "400", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "422", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) })
        }
    )
    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UserViewModel createUser(@Valid @RequestBody UserCreateRequest request) {
        return userService.createUser(request);
    }

    @Operation(summary = "Update User", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserViewModel.class)) }),
            @ApiResponse(responseCode = "400", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "404", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "422", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) })
        }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_UPDATE_USER + AUTH_SUFFIX)
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public UserViewModel updateUser(@PathVariable long id, @Valid @RequestBody UserUpdateRequest request) {
        return userService.updateUser(request, id);
    }

}
