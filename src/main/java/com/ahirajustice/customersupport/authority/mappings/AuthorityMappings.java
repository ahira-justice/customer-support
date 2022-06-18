package com.ahirajustice.customersupport.authority.mappings;

import com.ahirajustice.customersupport.common.entities.Authority;
import com.ahirajustice.customersupport.authority.viewmodels.AuthorityViewModel;
import org.mapstruct.Mapper;

@Mapper
public interface AuthorityMappings {

    AuthorityViewModel authorityToAuthorityViewModel(Authority authority);

}
