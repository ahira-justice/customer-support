package com.ahirajustice.customersupport.modules.authority.services;


import com.ahirajustice.customersupport.common.entities.Authority;
import com.ahirajustice.customersupport.modules.authority.viewmodels.AuthorityViewModel;

import java.util.List;

public interface AuthorityService {

    List<AuthorityViewModel> getAuthorities();

    AuthorityViewModel getAuthority(long id);

    Authority verifyAuthorityExists(long id);

}
