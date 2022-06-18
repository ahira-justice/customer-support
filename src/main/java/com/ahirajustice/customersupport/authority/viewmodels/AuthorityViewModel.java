package com.ahirajustice.customersupport.authority.viewmodels;

import com.ahirajustice.customersupport.common.viewmodels.BaseViewModel;
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
public class AuthorityViewModel extends BaseViewModel {

    private String name;
    private boolean isSystem;

}
