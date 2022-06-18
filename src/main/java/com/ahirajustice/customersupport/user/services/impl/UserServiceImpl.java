package com.ahirajustice.customersupport.user.services.impl;

import com.ahirajustice.customersupport.common.entities.Role;
import com.ahirajustice.customersupport.common.entities.User;
import com.ahirajustice.customersupport.common.enums.Roles;
import com.ahirajustice.customersupport.common.exceptions.BadRequestException;
import com.ahirajustice.customersupport.common.exceptions.NotFoundException;
import com.ahirajustice.customersupport.common.repositories.RoleRepository;
import com.ahirajustice.customersupport.common.repositories.UserRepository;
import com.ahirajustice.customersupport.user.mappings.UserMappings;
import com.ahirajustice.customersupport.user.queries.SearchUsersQuery;
import com.ahirajustice.customersupport.user.requests.UserCreateRequest;
import com.ahirajustice.customersupport.user.requests.UserUpdateRequest;
import com.ahirajustice.customersupport.user.services.UserService;
import com.ahirajustice.customersupport.user.viewmodels.UserViewModel;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    
    private final UserMappings mappings = Mappers.getMapper(UserMappings.class);

    @Override
     public Page<UserViewModel> searchUsers(SearchUsersQuery query) {
        return userRepository.findAll(query.getPredicate(), query.getPageable()).map(mappings::userToUserViewModel);
    }

    @Override
    public UserViewModel getUser(long id) {
        Optional<User> userExists = userRepository.findById(id);

        if (!userExists.isPresent()) {
            throw new NotFoundException(String.format("User with id: '%d' does not exist", id));
        }

        return mappings.userToUserViewModel(userExists.get());
    }

    @Override
    public UserViewModel createUser(UserCreateRequest request) {
        Optional<User> userExists = userRepository.findByUsername(request.getUsername());

        if (userExists.isPresent()) {
            throw new BadRequestException(String.format("User with username: '%s' already exists", request.getUsername()));
        }

        User user = mappings.userCreateRequestToUser(request);

        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        Role userRole = roleRepository.findByName(Roles.USER.name()).orElse(null);
        user.setPassword(encryptedPassword);
        user.setRole(userRole);

        return mappings.userToUserViewModel(userRepository.save(user));
    }

    @Override
    public UserViewModel updateUser(UserUpdateRequest request, long id) {
        Optional<User> userExists = userRepository.findById(id);

        if (!userExists.isPresent()) {
            throw new NotFoundException(String.format("User with id: '%d' does not exist", id));
        }

        User user = userExists.get();

        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User updatedUser = userRepository.save(user);

        return mappings.userToUserViewModel(updatedUser);
    }

}
