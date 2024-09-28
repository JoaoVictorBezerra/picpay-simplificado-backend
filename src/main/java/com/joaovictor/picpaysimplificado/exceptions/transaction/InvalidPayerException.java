package com.joaovictor.picpaysimplificado.exceptions.transaction;

import com.joaovictor.picpaysimplificado.exceptions.BusinessException;

public class InvalidPayerException extends BusinessException {
    public InvalidPayerException(String message, String title) {
        super(message, title);
    }
}
