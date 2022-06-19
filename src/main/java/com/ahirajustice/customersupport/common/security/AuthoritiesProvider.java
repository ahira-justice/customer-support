package com.ahirajustice.customersupport.common.security;


import com.ahirajustice.customersupport.common.constants.AuthorityConstants;
import com.ahirajustice.customersupport.common.entities.Authority;
import com.ahirajustice.customersupport.common.entities.Role;
import com.ahirajustice.customersupport.common.enums.Roles;

import java.util.HashSet;
import java.util.Set;

public class AuthoritiesProvider {

    // User authorities
    public static Authority CAN_VIEW_USER = new Authority(AuthorityConstants.CAN_VIEW_USER);
    public static Authority CAN_SEARCH_USERS = new Authority(AuthorityConstants.CAN_SEARCH_USERS, true);
    public static Authority CAN_CREATE_AGENT = new Authority(AuthorityConstants.CAN_CREATE_AGENT, true);
    public static Authority CAN_CREATE_SUPER_ADMIN = new Authority(AuthorityConstants.CAN_CREATE_SUPER_ADMIN, true);
    public static Authority CAN_UPDATE_USER = new Authority(AuthorityConstants.CAN_UPDATE_USER);

    // Conversation authorities
    public static Authority CAN_VIEW_CONVERSATION = new Authority(AuthorityConstants.CAN_VIEW_CONVERSATION);
    public static Authority CAN_SEARCH_CONVERSATIONS = new Authority(AuthorityConstants.CAN_SEARCH_CONVERSATIONS);
    public static Authority CAN_INITIATE_CONVERSATION = new Authority(AuthorityConstants.CAN_INITIATE_CONVERSATION);
    public static Authority CAN_CLOSE_CONVERSATION = new Authority(AuthorityConstants.CAN_CLOSE_CONVERSATION);

    // Message authorities
    public static Authority CAN_VIEW_MESSAGE = new Authority(AuthorityConstants.CAN_VIEW_MESSAGE);
    public static Authority CAN_SEARCH_MESSAGES = new Authority(AuthorityConstants.CAN_SEARCH_MESSAGES);
    public static Authority CAN_SEND_MESSAGE = new Authority(AuthorityConstants.CAN_SEND_MESSAGE);

    // Authority authorities
    public static Authority CAN_VIEW_AUTHORITY = new Authority(AuthorityConstants.CAN_VIEW_AUTHORITY, true);
    public static Authority CAN_VIEW_ALL_AUTHORITIES = new Authority(AuthorityConstants.CAN_VIEW_ALL_AUTHORITIES, true);

    // Role authorities
    public static Authority CAN_VIEW_ROLE = new Authority(AuthorityConstants.CAN_VIEW_ROLE, true);
    public static Authority CAN_VIEW_ALL_ROLES = new Authority(AuthorityConstants.CAN_VIEW_ALL_ROLES, true);
    public static Authority CAN_CREATE_ROLE = new Authority(AuthorityConstants.CAN_CREATE_ROLE, true);
    public static Authority CAN_UPDATE_ROLE = new Authority(AuthorityConstants.CAN_UPDATE_ROLE, true);

    public static Set<Authority> getAllAuthorities() {
        Set<Authority> authorities = new HashSet<>();

        // User authorities
        authorities.add(CAN_VIEW_USER);
        authorities.add(CAN_SEARCH_USERS);
        authorities.add(CAN_CREATE_AGENT);
        authorities.add(CAN_CREATE_SUPER_ADMIN);
        authorities.add(CAN_UPDATE_USER);

        // Conversation authorities
        authorities.add(CAN_VIEW_CONVERSATION);
        authorities.add(CAN_SEARCH_CONVERSATIONS);
        authorities.add(CAN_INITIATE_CONVERSATION);
        authorities.add(CAN_CLOSE_CONVERSATION);

        // Message authorities
        authorities.add(CAN_VIEW_MESSAGE);
        authorities.add(CAN_SEARCH_MESSAGES);
        authorities.add(CAN_SEND_MESSAGE);

        // Authority authorities
        authorities.add(CAN_VIEW_AUTHORITY);
        authorities.add(CAN_VIEW_ALL_AUTHORITIES);

        // Role authorities
        authorities.add(CAN_VIEW_ROLE);
        authorities.add(CAN_VIEW_ALL_ROLES);
        authorities.add(CAN_CREATE_ROLE);
        authorities.add(CAN_UPDATE_ROLE);

        return authorities;
    }

    private static Set<Authority> getUserAuthorities() {
        Set<Authority> authorities = new HashSet<>();

        // User authorities
        authorities.add(CAN_VIEW_USER);
        authorities.add(CAN_UPDATE_USER);

        // Conversation authorities
        authorities.add(CAN_VIEW_CONVERSATION);
        authorities.add(CAN_SEARCH_CONVERSATIONS);
        authorities.add(CAN_INITIATE_CONVERSATION);

        // Message authorities
        authorities.add(CAN_VIEW_MESSAGE);
        authorities.add(CAN_SEARCH_MESSAGES);
        authorities.add(CAN_SEND_MESSAGE);

        return authorities;
    }

    private static Set<Authority> getAgentAuthorities() {
        Set<Authority> authorities = new HashSet<>();

        // User authorities
        authorities.add(CAN_VIEW_USER);
        authorities.add(CAN_UPDATE_USER);

        // Conversation authorities
        authorities.add(CAN_VIEW_CONVERSATION);
        authorities.add(CAN_SEARCH_CONVERSATIONS);
        authorities.add(CAN_CLOSE_CONVERSATION);

        // Message authorities
        authorities.add(CAN_VIEW_MESSAGE);
        authorities.add(CAN_SEARCH_MESSAGES);
        authorities.add(CAN_SEND_MESSAGE);

        return authorities;
    }

    private static Set<Authority> getSuperAdminAuthorities() {
        return getAllAuthorities();
    }

    public static Set<Role> getDefaultRoles() {
        Set<Role> roles = new HashSet<>();

        Role user = new Role(Roles.USER.name());
        user.setAuthorities(getUserAuthorities());
        roles.add(user);

        Role agent = new Role(Roles.AGENT.name());
        agent.setAuthorities(getAgentAuthorities());
        roles.add(agent);

        Role superAdmin = new Role(Roles.SUPERADMIN.name(), true);
        superAdmin.setAuthorities(getSuperAdminAuthorities());
        roles.add(superAdmin);

        return roles;
    }

}
