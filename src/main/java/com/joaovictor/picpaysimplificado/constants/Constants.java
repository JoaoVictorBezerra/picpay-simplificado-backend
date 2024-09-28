package com.joaovictor.picpaysimplificado.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final Integer HTTP_TIME_OUT = 20000;


    public static final String INSUFFICIENT_BALANCE_TITLE = "Insufficient Balance!";
    public static final String INSUFFICIENT_BALANCE_DETAIL = "The payer don't have sufficient balance for this operation.";

    public static final String INVALID_PAYER_TITLE = "Invalid Payer!";
    public static final String INVALID_PAYER_DETAIL = "Traders can't do transaction.";

    public static final String USER_ALREADY_EXISTS_TITLE = "User already exists!";
    public static final String USER_ALREADY_EXISTS_DETAIL = "Email or document already exists.";

    public static final String USER_NOT_FOUND_TITLE = "User not found!";
    public static final String USER_NOT_FOUND_DETAIL = "This user doesn't exists";
}
