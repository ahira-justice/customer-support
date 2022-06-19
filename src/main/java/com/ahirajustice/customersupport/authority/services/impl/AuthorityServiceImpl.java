package com.ahirajustice.customersupport.authority.services.impl;

import com.ahirajustice.customersupport.authority.services.AuthorityService;
import com.ahirajustice.customersupport.authority.viewmodels.AuthorityViewModel;
import com.ahirajustice.customersupport.common.entities.Authority;
import com.ahirajustice.customersupport.common.exceptions.NotFoundException;
import com.ahirajustice.customersupport.common.repositories.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Override
    public List<AuthorityViewModel> getAuthorities() {
        List<Authority> authorities = authorityRepository.findAll();
        return authorities.stream().map(AuthorityViewModel::from).collect(Collectors.toList());
    }

    @Override
    public AuthorityViewModel getAuthority(long id) {
        Authority authority = verifyAuthorityExists(id);
        return AuthorityViewModel.from(authority);
    }

    @Override
    public Authority verifyAuthorityExists(long id) {
        Optional<Authority> authorityExists = authorityRepository.findById(id);

        if (!authorityExists.isPresent()) {
            throw new NotFoundException(String.format("Authority with id: '%d' does not exist", id));
        }

        return authorityExists.get();
    }

}
