package com.joaovictor.picpaysimplificado.constants;

public final class Constants {
    private Constants() {
        throw new IllegalArgumentException("This is an utility class!");
    }

    public static final String INSUFFICIENT_BALANCE_TITLE = "Insufficient Balance!";
    public static final String INSUFFICIENT_BALANCE_DETAIL = "The payer don't have sufficient balance for this operation.";

    public static final String INVALID_PAYER_TITLE = "Invalid Payer!";
    public static final String INVALID_PAYER_DETAIL = "Traders can't do transaction.";

    public static final String USER_ALREADY_EXISTS_TITLE = "User already exists!";
    public static final String USER_ALREADY_EXISTS_DETAIL = "Email or document already exists.";

}
