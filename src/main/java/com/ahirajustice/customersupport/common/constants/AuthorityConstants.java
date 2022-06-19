package com.ahirajustice.customersupport.common.constants;

public class AuthorityConstants {

    public static final String AUTH_PREFIX = "hasAuthority('";
    public static final String AUTH_SUFFIX = "')";

    // User authorities
    public static final String CAN_VIEW_USER = "CAN_VIEW_USER";
    public static final String CAN_SEARCH_USERS = "CAN_SEARCH_USERS";
    public static final String CAN_CREATE_AGENT = "CAN_CREATE_AGENT";
    public static final String CAN_CREATE_SUPER_ADMIN = "CAN_CREATE_SUPER_ADMIN";
    public static final String CAN_UPDATE_USER = "CAN_UPDATE_USER";

    //Conversation authorities
    public static final String CAN_VIEW_CONVERSATION = "CAN_VIEW_CONVERSATION";
    public static final String CAN_SEARCH_CONVERSATIONS = "CAN_SEARCH_CONVERSATIONS";
    public static final String CAN_INITIATE_CONVERSATION = "CAN_INITIATE_CONVERSATION";
    public static final String CAN_CLOSE_CONVERSATION = "CAN_CLOSE_CONVERSATION";

    //Message authorities
    public static final String CAN_VIEW_MESSAGE = "CAN_VIEW_MESSAGE";
    public static final String CAN_SEARCH_MESSAGES = "CAN_SEARCH_MESSAGES";
    public static final String CAN_SEND_MESSAGE = "CAN_SEND_MESSAGE";

    // Authority authorities
    public static final String CAN_VIEW_AUTHORITY = "CAN_VIEW_AUTHORITY";
    public static final String CAN_VIEW_ALL_AUTHORITIES = "CAN_VIEW_ALL_AUTHORITIES";

    // Role authorities
    public static final String CAN_VIEW_ROLE = "CAN_VIEW_ROLE";
    public static final String CAN_VIEW_ALL_ROLES = "CAN_VIEW_ALL_ROLES";
    public static final String CAN_CREATE_ROLE = "CAN_CREATE_ROLE";
    public static final String CAN_UPDATE_ROLE = "CAN_UPDATE_ROLE";

}
