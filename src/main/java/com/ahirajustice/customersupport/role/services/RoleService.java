package com.ahirajustice.customersupport.role.services;


import com.ahirajustice.customersupport.role.requests.CreateRoleRequest;
import com.ahirajustice.customersupport.role.requests.UpdateRoleRequest;
import com.ahirajustice.customersupport.role.viewmodels.RoleViewModel;

import java.util.List;

public interface RoleService {

    List<RoleViewModel> getRoles();

    RoleViewModel getRole(long id);

    RoleViewModel createRole(CreateRoleRequest request);

    RoleViewModel updateRole(UpdateRoleRequest request, long id);

}
