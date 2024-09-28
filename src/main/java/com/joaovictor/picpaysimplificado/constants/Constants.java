package com.joaovictor.picpaysimplificado.constants;

public final class Constants {
    private Constants() {
        throw new IllegalArgumentException("This is an utility class!");
    }

    public static final String INSUFFICIENT_BALANCE_TITLE = "Insufficient Balance!";
    public static final String INSUFFICIENT_BALANCE_DETAIL = "The payer don't have sufficient balance for this operation";
}
