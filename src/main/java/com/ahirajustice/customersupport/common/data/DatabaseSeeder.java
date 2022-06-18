package com.ahirajustice.customersupport.common.data;

import com.ahirajustice.customersupport.common.entities.Authority;
import com.ahirajustice.customersupport.common.entities.Role;
import com.ahirajustice.customersupport.common.entities.User;
import com.ahirajustice.customersupport.common.enums.Roles;
import com.ahirajustice.customersupport.common.properties.AppProperties;
import com.ahirajustice.customersupport.common.repositories.AuthorityRepository;
import com.ahirajustice.customersupport.common.repositories.RoleRepository;
import com.ahirajustice.customersupport.common.repositories.UserRepository;
import com.ahirajustice.customersupport.common.security.AuthoritiesProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    private final BCryptPasswordEncoder passwordEncoder;
    private final AppProperties appProperties;

    private void installAuthorities() {
        Set<Authority> authorities = AuthoritiesProvider.getAllAuthorities();

        for (Authority authority : authorities) {
            try {
                Optional<Authority> authorityExists = authorityRepository.findByName(authority.getName());

                if (authorityExists.isPresent()) {
                    continue;
                }

                authorityRepository.save(authority);
            } catch (Exception ignored) {

            }
        }
    }

    private void installDefaultRoles() {
        Set<Role> roles = AuthoritiesProvider.getDefaultRoles();

        List<Authority> authorities = authorityRepository.findAll();

        for (Role role : roles) {
            Optional<Role> roleExists = roleRepository.findByName(role.getName());

            Set<Authority> roleAuthorities = new HashSet<>();

            for (Authority roleAuthority : role.getAuthorities()) {
                for (Authority authority : authorities) {
                    if (authority.getName().equals(roleAuthority.getName())) {
                        roleAuthorities.add(authority);
                    }
                }
            }

            if (roleExists.isPresent()) {
                Role currentRole = roleExists.get();
                currentRole.setAuthorities(roleAuthorities);
                roleRepository.save(currentRole);

            } else {
                role.setAuthorities(roleAuthorities);
                roleRepository.save(role);
            }
        }
    }

    private void seedSuperAdminUser() {
        try {
            Optional<User> superAdminExists = userRepository.findByUsername(appProperties.getSuperuserEmail());

            if (superAdminExists.isPresent()) {
                return;
            }

            User superAdmin = new User();
            Role superAdminRole = roleRepository.findByName(Roles.SUPERADMIN.name()).orElse(null);
            superAdmin.setEmail(appProperties.getSuperuserEmail());
            superAdmin.setUsername(superAdmin.getEmail());
            superAdmin.setFirstName(appProperties.getSuperuserFirstName());
            superAdmin.setLastName(appProperties.getSuperuserLastName());
            superAdmin.setEmailVerified(true);
            superAdmin.setPassword(passwordEncoder.encode(appProperties.getSuperuserPassword()));
            superAdmin.setRole(superAdminRole);

            userRepository.save(superAdmin);
        } catch (Exception ignored) {

        }
    }

    @Override
    public void run(ApplicationArguments args) {
        installAuthorities();
        installDefaultRoles();
        seedSuperAdminUser();
    }

}
