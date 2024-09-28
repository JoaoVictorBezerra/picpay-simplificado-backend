package com.joaovictor.picpaysimplificado.exceptions.transaction;

import com.joaovictor.picpaysimplificado.exceptions.BusinessException;

public class InsufficientBalance extends BusinessException {
    public InsufficientBalance(String message, String title) {
        super(message, title);
    }
}
