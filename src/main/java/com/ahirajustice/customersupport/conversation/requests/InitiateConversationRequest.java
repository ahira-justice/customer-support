package com.ahirajustice.customersupport.conversation.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InitiateConversationRequest {

    @NotBlank(message = "messageBody is required")
    private String messageBody;

}
