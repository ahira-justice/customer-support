package com.ahirajustice.customersupport.user.mappings;

import com.ahirajustice.customersupport.common.entities.User;
import com.ahirajustice.customersupport.user.requests.UserCreateRequest;
import com.ahirajustice.customersupport.user.viewmodels.UserViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMappings {

    @Mapping(target = "role", source = "role.name")
    UserViewModel userToUserViewModel(User user);

    User userCreateRequestToUser(UserCreateRequest userCreateRequest);

}
