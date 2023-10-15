package com.ahirajustice.customersupport.modules.role.services.impl;

import com.ahirajustice.customersupport.modules.authority.services.AuthorityService;
import com.ahirajustice.customersupport.common.entities.Authority;
import com.ahirajustice.customersupport.common.entities.Role;
import com.ahirajustice.customersupport.common.entities.User;
import com.ahirajustice.customersupport.common.error.Error;
import com.ahirajustice.customersupport.common.exceptions.BadRequestException;
import com.ahirajustice.customersupport.common.exceptions.ForbiddenException;
import com.ahirajustice.customersupport.common.exceptions.NotFoundException;
import com.ahirajustice.customersupport.common.exceptions.ValidationException;
import com.ahirajustice.customersupport.common.repositories.AuthorityRepository;
import com.ahirajustice.customersupport.common.repositories.RoleRepository;
import com.ahirajustice.customersupport.common.utils.CommonUtils;
import com.ahirajustice.customersupport.modules.role.viewmodels.RoleViewModel;
import com.ahirajustice.customersupport.modules.user.services.CurrentUserService;
import com.ahirajustice.customersupport.modules.role.requests.CreateRoleRequest;
import com.ahirajustice.customersupport.modules.role.requests.UpdateRoleRequest;
import com.ahirajustice.customersupport.modules.role.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final AuthorityService authorityService;
    private final CurrentUserService currentUserService;

    public List<RoleViewModel> getRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(RoleViewModel::from).collect(Collectors.toList());
    }

    public RoleViewModel getRole(long id) {
        Optional<Role> roleExists = roleRepository.findById(id);

        if (!roleExists.isPresent()) {
            throw new NotFoundException(String.format("Role with id: '%d' does not exist", id));
        }

        return RoleViewModel.from(roleExists.get());
    }

    @Override
    public RoleViewModel createRole(CreateRoleRequest request) {
        validate(request.getName());
        User currentUser = currentUserService.getCurrentUser();
        if (request.isSystem() && !currentUser.getRole().isSystem()){
            throw new ForbiddenException(currentUser.getUsername());
        }

        Optional<Role> roleExists = roleRepository.findByName(request.getName());

        if (roleExists.isPresent()) {
            throw new BadRequestException(String.format("Role with name: '%s' already exists", request.getName()));
        }

        Set<Authority> authorities = new HashSet<>();
        for (long authorityId : request.getAuthorityIds()) {
            Authority authority = authorityService.verifyAuthorityExists(authorityId);

            if (!request.isSystem() && authority.isSystem()){
                throw new BadRequestException("Cannot add system authority to non-system role");
            }

            authorities.add(authority);
        }

        Role role = Role.builder()
                .name(request.getName())
                .isSystem(request.isSystem())
                .build();

        role.setAuthorities(authorities);

        return RoleViewModel.from(roleRepository.save(role));
    }

    @Override
    public RoleViewModel updateRole(UpdateRoleRequest request, long id) {
        validate(request.getName());
        Optional<Role> roleExists = roleRepository.findById(id);

        if (!roleExists.isPresent()) {
            throw new NotFoundException(String.format("Role with id: '%d' does not exist", id));
        }

        Optional<Role> roleNameExists = roleRepository.findByName(request.getName());

        if (roleNameExists.isPresent() && roleNameExists.get().getId() != id){
            throw new BadRequestException(String.format("Role with name: '%s' already exists", request.getName()));
        }

        Role role = roleExists.get();

        Set<Authority> authorities = new HashSet<>();
        for (long authorityId : request.getAuthorityIds()) {
            authorities.add(authorityRepository.findById(authorityId).orElse(null));
        }

        role.setName(request.getName());
        role.setAuthorities(authorities);

        return RoleViewModel.from(roleRepository.save(role));
    }

    private void validate(String name) {
        List<Error> errors = new ArrayList<>();

        if (!StringUtils.isAllUpperCase(name))
            errors.add(Error.create("name", "name must be uppercase", name));

        if (CommonUtils.containsSpecialCharactersAndNumbers(name))
            errors.add(Error.create("name", "name must not contain special characters or numbers", name));

        throw new ValidationException(errors);
    }

}
