package org.fomenko.exception;

public class UserByNameNotFoundException extends RuntimeException {
    public UserByNameNotFoundException(String name) {
        super("User with name " + name + " not found");
    }
}
