package com.ahirajustice.customersupport.conversation.requests;


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

    @Min(1)
    private long conversationId;

}
