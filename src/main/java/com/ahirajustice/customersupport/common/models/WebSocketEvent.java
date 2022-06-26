package com.ahirajustice.customersupport.common.models;

import com.ahirajustice.customersupport.common.enums.WebSocketEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketEvent {

    private long eventId;
    private WebSocketEventType eventType;

}
