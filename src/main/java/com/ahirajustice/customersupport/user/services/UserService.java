package com.ahirajustice.customersupport.user.services;

import com.ahirajustice.customersupport.user.queries.SearchUsersQuery;
import com.ahirajustice.customersupport.user.requests.UserCreateRequest;
import com.ahirajustice.customersupport.user.requests.UserUpdateRequest;
import com.ahirajustice.customersupport.user.viewmodels.UserViewModel;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserViewModel> searchUsers(SearchUsersQuery query);

    UserViewModel getUser(long id);

    UserViewModel createUser(UserCreateRequest request);

    UserViewModel updateUser(UserUpdateRequest request, long id);

}
