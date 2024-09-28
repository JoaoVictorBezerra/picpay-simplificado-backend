package com.joaovictor.picpaysimplificado.exceptions.user;

import com.joaovictor.picpaysimplificado.exceptions.BusinessException;

public class UserAlreadyExistsException extends BusinessException {

    public UserAlreadyExistsException(String message, String title) {
        super(message, title);
    }
}
