package com.ahirajustice.customersupport.role.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleCreateRequest {

    @NotBlank(message = "name is required")
    private String name;
    private boolean isSystem;
    @NotEmpty(message = "At least one authority is required")
    private List<@Min(1) Long> authorityIds;

}
