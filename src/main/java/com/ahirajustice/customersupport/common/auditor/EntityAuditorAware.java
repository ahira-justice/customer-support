package com.ahirajustice.customersupport.common.auditor;

import com.ahirajustice.customersupport.common.entities.User;
import com.ahirajustice.customersupport.modules.user.services.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EntityAuditorAware implements AuditorAware<String> {

    private final CurrentUserService currentUserService;


    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            User currentUser = currentUserService.getCurrentUser();
            return Optional.of(currentUser.getUsername());
        }
        catch (Exception ex) {
            return Optional.of("SYSTEM");
        }

    }

}
