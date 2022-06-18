package com.ahirajustice.customersupport.common.constants;

public class AuthorityConstants {

    public static final String AUTH_PREFIX = "hasAuthority('";
    public static final String AUTH_SUFFIX = "')";

    // User authorities
    public static final String CAN_VIEW_USER = "CAN_VIEW_USER";
    public static final String CAN_SEARCH_USERS = "CAN_SEARCH_USERS";
    public static final String CAN_CREATE_USER = "CAN_CREATE_USER";
    public static final String CAN_CREATE_AGENT = "CAN_CREATE_AGENT";
    public static final String CAN_CREATE_SUPER_ADMIN = "CAN_CREATE_SUPER_ADMIN";
    public static final String CAN_UPDATE_USER = "CAN_UPDATE_USER";

    // Authority authorities
    public static final String CAN_VIEW_AUTHORITY = "CAN_VIEW_AUTHORITY";
    public static final String CAN_VIEW_ALL_AUTHORITIES = "CAN_VIEW_ALL_AUTHORITIES";

    // Role authorities
    public static final String CAN_VIEW_ROLE = "CAN_VIEW_ROLE";
    public static final String CAN_VIEW_ALL_ROLES = "CAN_VIEW_ALL_ROLES";
    public static final String CAN_CREATE_ROLE = "CAN_CREATE_ROLE";
    public static final String CAN_UPDATE_ROLE = "CAN_UPDATE_ROLE";

}
