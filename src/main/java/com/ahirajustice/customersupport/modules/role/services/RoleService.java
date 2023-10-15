package com.ahirajustice.customersupport.modules.role.services;


import com.ahirajustice.customersupport.modules.role.viewmodels.RoleViewModel;
import com.ahirajustice.customersupport.modules.role.requests.CreateRoleRequest;
import com.ahirajustice.customersupport.modules.role.requests.UpdateRoleRequest;

import java.util.List;

public interface RoleService {

    List<RoleViewModel> getRoles();

    RoleViewModel getRole(long id);

    RoleViewModel createRole(CreateRoleRequest request);

    RoleViewModel updateRole(UpdateRoleRequest request, long id);

}
