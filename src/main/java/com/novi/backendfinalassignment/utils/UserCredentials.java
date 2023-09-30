package com.novi.backendfinalassignment.utils;

public interface UserCredentials {
    void setPassword(String password);
    String getPassword();
    boolean isPasswordValid(String password);
    void changePassword(String newPassword);
}

