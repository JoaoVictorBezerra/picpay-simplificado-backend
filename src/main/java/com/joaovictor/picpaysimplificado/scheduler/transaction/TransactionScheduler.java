package com.joaovictor.picpaysimplificado.scheduler.transaction;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Log4j2
public class TransactionScheduler {

//    @Scheduled(cron = "")
    public void verifyIfHaveUncompletedTransactions() {
        log.info("TRANSACTION SCHEDULER:: Init method");
    }

}
