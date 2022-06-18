package com.ahirajustice.customersupport.user.viewmodels;

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
public class UserViewModel extends BaseViewModel {

    private String email;
    private String firstName;
    private String lastName;
    private String role;

}
