package com.ahirajustice.customersupport.modules.conversation.requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CloseConversationRequest {

    @Min(value = 1, message = "conversationId is required; must be greater than or equal to 1")
    private long conversationId;

}
