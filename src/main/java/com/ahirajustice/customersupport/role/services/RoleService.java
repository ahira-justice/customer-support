package com.ahirajustice.customersupport.role.services;


import com.ahirajustice.customersupport.role.requests.RoleCreateRequest;
import com.ahirajustice.customersupport.role.requests.RoleUpdateRequest;
import com.ahirajustice.customersupport.role.viewmodels.RoleViewModel;

import java.util.List;

public interface RoleService {

    List<RoleViewModel> getRoles();

    RoleViewModel getRole(long id);

    RoleViewModel createRole(RoleCreateRequest request);

    RoleViewModel updateRole(RoleUpdateRequest request, long id);

}
