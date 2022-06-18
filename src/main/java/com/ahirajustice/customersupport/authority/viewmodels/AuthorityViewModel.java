package com.ahirajustice.customersupport.authority.viewmodels;


import com.ahirajustice.customersupport.common.viewmodels.BaseViewModel;

public class AuthorityViewModel extends BaseViewModel {

    private String name;

    private boolean isSystem;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }

}
