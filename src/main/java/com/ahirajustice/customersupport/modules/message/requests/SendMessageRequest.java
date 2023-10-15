package com.ahirajustice.customersupport.modules.message.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {

    @Min(value = 1, message = "conversationId is required; must be greater than or equal to 1")
    private long conversationId;
    @NotBlank(message = "messageBody is required")
    private String messageBody;

}
