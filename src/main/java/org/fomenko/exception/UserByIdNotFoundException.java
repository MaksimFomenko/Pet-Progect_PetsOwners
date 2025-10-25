package org.fomenko.exception;

public class UserByIdNotFoundException extends RuntimeException {
    public UserByIdNotFoundException(Integer id) {
        super("User with id " + id + " not found");
    }
}
