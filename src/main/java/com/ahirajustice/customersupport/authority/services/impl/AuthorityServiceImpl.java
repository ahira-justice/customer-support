package com.ahirajustice.customersupport.authority.services.impl;

import com.ahirajustice.customersupport.common.entities.Authority;
import com.ahirajustice.customersupport.common.exceptions.NotFoundException;
import com.ahirajustice.customersupport.common.repositories.AuthorityRepository;
import com.ahirajustice.customersupport.authority.mappings.AuthorityMappings;
import com.ahirajustice.customersupport.authority.services.AuthorityService;
import com.ahirajustice.customersupport.authority.viewmodels.AuthorityViewModel;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    private final AuthorityMappings mappings = Mappers.getMapper(AuthorityMappings.class);

    @Override
    public List<AuthorityViewModel> getAuthorities() {
        List<AuthorityViewModel> responses = new ArrayList<>();

        List<Authority> authorities = authorityRepository.findAll();

        for (Authority authority : authorities) {
            responses.add(mappings.authorityToAuthorityViewModel(authority));
        }

        return responses;
    }

    @Override
    public AuthorityViewModel getAuthority(long id) {
        Authority authority = verifyAuthorityExists(id);
        return mappings.authorityToAuthorityViewModel(authority);
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
