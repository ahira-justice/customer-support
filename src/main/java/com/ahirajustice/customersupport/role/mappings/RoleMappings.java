package com.ahirajustice.customersupport.role.mappings;

import com.ahirajustice.customersupport.common.entities.Role;
import com.ahirajustice.customersupport.role.requests.RoleCreateRequest;
import com.ahirajustice.customersupport.role.viewmodels.RoleViewModel;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMappings {

    RoleViewModel roleToRoleViewModel(Role role);

    Role roleCreateRequestToRole(RoleCreateRequest roleCreateRequest);

}
