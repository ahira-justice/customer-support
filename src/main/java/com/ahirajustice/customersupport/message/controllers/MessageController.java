package com.ahirajustice.customersupport.message.controllers;

import com.ahirajustice.customersupport.common.constants.AuthorityConstants;
import com.ahirajustice.customersupport.common.error.ErrorResponse;
import com.ahirajustice.customersupport.common.error.ValidationErrorResponse;
import com.ahirajustice.customersupport.message.queries.SearchMessagesByConversationQuery;
import com.ahirajustice.customersupport.message.queries.SearchMessagesQuery;
import com.ahirajustice.customersupport.message.requests.SendMessageRequest;
import com.ahirajustice.customersupport.message.services.MessageService;
import com.ahirajustice.customersupport.message.viewmodels.MessageViewModel;
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

@Tag(name = "Messages")
@RestController
@RequestMapping("api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @Operation(summary = "Send Message", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MessageViewModel.class)) }),
            @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "422", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)) })
        }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_SEND_MESSAGE + AUTH_SUFFIX)
    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public MessageViewModel sendMessage(@Valid @RequestBody SendMessageRequest request) {
        return messageService.sendMessage(request);
    }

    @Operation(summary = "Search Messages", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MessageViewModel.class)) }),
                    @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
                    @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_SEARCH_MESSAGES + AUTH_SUFFIX)
    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<MessageViewModel> searchMessages(@Valid SearchMessagesQuery query) {
        return messageService.searchMessages(query);
    }

    @Operation(summary = "Search Messages By Conversation", security = { @SecurityRequirement(name = "bearer") })
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MessageViewModel.class)) }),
                    @ApiResponse(responseCode = "401", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
                    @ApiResponse(responseCode = "403", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            }
    )
    @PreAuthorize(AUTH_PREFIX + AuthorityConstants.CAN_SEARCH_MESSAGES + AUTH_SUFFIX)
    @RequestMapping(path = "by-conversation", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<MessageViewModel> searchMessagesByConversation(@Valid SearchMessagesByConversationQuery query) {
        return messageService.searchMessagesByConversation(query);
    }

}
