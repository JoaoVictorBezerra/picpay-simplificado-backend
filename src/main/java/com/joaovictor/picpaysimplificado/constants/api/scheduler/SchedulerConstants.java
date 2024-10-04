package com.joaovictor.picpaysimplificado.constants.api.scheduler;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SchedulerConstants {
    public static final String FAIL_TRANSACTIONS = "${cron.fail.transactions}";
}
