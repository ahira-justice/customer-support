package com.ahirajustice.customersupport.modules.role.viewmodels;

import com.ahirajustice.customersupport.modules.authority.viewmodels.AuthorityViewModel;
import com.ahirajustice.customersupport.common.entities.Role;
import com.ahirajustice.customersupport.common.viewmodels.BaseViewModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleViewModel extends BaseViewModel {

    private String name;
    private boolean isSystem;
    private List<AuthorityViewModel> authorities;

    public static RoleViewModel from(Role role) {
        RoleViewModel response = new RoleViewModel();

        BeanUtils.copyProperties(role, response);
        response.setAuthorities(
                role.getAuthorities().stream()
                        .map(AuthorityViewModel::from).collect(Collectors.toList())
        );

        return response;
    }
}
