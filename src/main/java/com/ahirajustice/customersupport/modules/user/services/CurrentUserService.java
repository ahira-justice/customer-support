package com.ahirajustice.customersupport.modules.user.services;


import com.ahirajustice.customersupport.common.entities.User;
import com.ahirajustice.customersupport.common.enums.Roles;

public interface CurrentUserService {
    
    User getCurrentUser();

    boolean currentUserHasRole(Roles role);

}
