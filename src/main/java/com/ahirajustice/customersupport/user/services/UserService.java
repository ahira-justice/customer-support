package com.ahirajustice.customersupport.user.services;

import com.ahirajustice.customersupport.common.entities.User;
import com.ahirajustice.customersupport.common.enums.Roles;
import com.ahirajustice.customersupport.user.queries.SearchUsersQuery;
import com.ahirajustice.customersupport.user.requests.CreateUserRequest;
import com.ahirajustice.customersupport.user.requests.UpdateUserRequest;
import com.ahirajustice.customersupport.user.viewmodels.UserViewModel;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserViewModel> searchUsers(SearchUsersQuery query);

    UserViewModel getUser(long id);

    UserViewModel createUser(CreateUserRequest request);

    User createUser(CreateUserRequest request, Roles role);

    UserViewModel updateUser(UpdateUserRequest request, long id);

}
