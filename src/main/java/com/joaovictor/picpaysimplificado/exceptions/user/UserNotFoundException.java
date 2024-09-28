package com.joaovictor.picpaysimplificado.exceptions.user;

import com.joaovictor.picpaysimplificado.exceptions.BusinessException;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String message, String title) {
        super(message, title);
    }
}
