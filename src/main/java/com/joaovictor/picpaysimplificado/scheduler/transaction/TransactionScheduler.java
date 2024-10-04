package com.joaovictor.picpaysimplificado.scheduler.transaction;

import com.joaovictor.picpaysimplificado.constants.api.scheduler.SchedulerConstants;
import com.joaovictor.picpaysimplificado.service.interfac.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Log4j2
@RequiredArgsConstructor
public class TransactionScheduler {
    private final TransactionService transactionService;

    @Scheduled(cron = SchedulerConstants.FAIL_TRANSACTIONS)
    public void verifyIfHaveUncompletedTransactions() {
        log.info("TRANSACTION SCHEDULER:: Init method");
        try {
            var fails = transactionService.failTransactions();
            log.info("TRANSACTION SCHEDULER:: Fail total of {} transactions from yesterday", fails);
        } catch (Exception e) {
            log.error("TRANSACTION SCHEDULER:: Error while executing scheduler");
        }
    }

}
