package com.edu_netcracker.cmp.entities.users;

public enum Permission {
    ADMIN("admin"),
    HR("hr"),
    USER("user");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
