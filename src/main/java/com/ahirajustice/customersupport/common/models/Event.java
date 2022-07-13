package com.ahirajustice.customersupport.common.models;

import com.ahirajustice.customersupport.common.enums.EventType;
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
public class Event {

    private long eventId;
    private EventType eventType;
    private Object payload;

}
