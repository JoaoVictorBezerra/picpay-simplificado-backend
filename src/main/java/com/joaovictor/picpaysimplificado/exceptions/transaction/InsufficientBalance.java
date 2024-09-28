package com.joaovictor.picpaysimplificado.exceptions.transaction;

import com.joaovictor.picpaysimplificado.constants.Constants;
import com.joaovictor.picpaysimplificado.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class InsufficientBalance extends BusinessException {

    @Override
    public ProblemDetail toProblemDetail() {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(Constants.INSUFFICIENT_BALANCE_TITLE);
        problemDetail.setDetail(Constants.INSUFFICIENT_BALANCE_DETAIL);
        return toProblemDetail();
    }
}