package com.ahirajustice.customersupport.role.viewmodels;

import com.ahirajustice.customersupport.common.viewmodels.BaseViewModel;
import com.ahirajustice.customersupport.authority.viewmodels.AuthorityViewModel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RoleViewModel extends BaseViewModel {

    private String name;

    private boolean isSystem;

    private Set<AuthorityViewModel> authorities = new HashSet<>();

}
