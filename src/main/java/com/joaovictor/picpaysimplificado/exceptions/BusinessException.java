package com.joaovictor.picpaysimplificado.exceptions;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final String title;

    public BusinessException(String message, String title) {
        super(message);
        this.title = title;
    }
}
