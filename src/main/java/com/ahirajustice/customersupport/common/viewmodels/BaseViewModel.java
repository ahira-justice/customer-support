package com.ahirajustice.customersupport.common.viewmodels;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseViewModel {

    private long id;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

}
