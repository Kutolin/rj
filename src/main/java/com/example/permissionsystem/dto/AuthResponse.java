// src/main/java/com/example/permissionsystem/dto/AuthResponse.java
package com.example.permissionsystem.dto;

import java.util.Set;

public class AuthResponse {
    private boolean success;
    private String message;
    private Set<String> permissions;

    // Constructors
    public AuthResponse() {}

    public AuthResponse(boolean success, String message, Set<String> permissions) {
        this.success = success;
        this.message = message;
        this.permissions = permissions;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
