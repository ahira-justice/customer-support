package com.ahirajustice.customersupport.conversation.controllers;

import com.ahirajustice.customersupport.common.constants.AuthorityConstants;
import com.ahirajustice.customersupport.common.error.ErrorResponse;
import com.ahirajustice.customersupport.common.error.ValidationErrorResponse;
import com.ahirajustice.customersupport.conversation.queries.SearchConversationsQuery;
import com.ahirajustice.customersupport.conversation.requests.CloseConversationRequest;
import com.ahirajustice.customersupport.conversation.requests.InitiateConversationRequest;
import com.ahirajustice.customersupport.conversation.services.ConversationService;
import com.ahirajustice.customersupport.conversation.viewmodels.ConversationViewModel;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.ahirajustice.customersupport.common.constants.AuthorityConstants.AUTH_PREFIX;
import static com.ahirajustice.customersupport.common.constants.AuthorityConstants.AUTH_SUFFIX;

@Tag(name = "Conversations")
@RestController
@RequestMapping("api/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;

    @Operation(summary = "Initiate Conversation", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ConversationViewModel.class)) }),
            @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "422", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) })
        }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_CLOSE_CONVERSATION + AUTH_SUFFIX)
    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ConversationViewModel initiateConversation(@Valid @RequestBody InitiateConversationRequest request) {
        return conversationService.initiateConversation(request);
    }

    @Operation(summary = "Search Conversations", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ConversationViewModel.class)) }),
                    @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
                    @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_SEARCH_CONVERSATIONS + AUTH_SUFFIX)
    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<ConversationViewModel> searchConversations(@Valid @RequestBody SearchConversationsQuery query) {
        return conversationService.searchConversations(query);
    }

    @Operation(summary = "Close Conversation", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ConversationViewModel.class)) }),
                    @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
                    @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
                    @ApiResponse(responseCode = "422", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) })
            }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_INITIATE_CONVERSATION + AUTH_SUFFIX)
    @RequestMapping(path = "/close", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ConversationViewModel closeConversation(@Valid @RequestBody CloseConversationRequest request) {
        return conversationService.closeConversation(request);
    }

}
