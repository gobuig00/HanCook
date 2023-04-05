package com.hancook.multicrawler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
//@RequestMapping("/")
public class SchedulerController {

    // Everyday 00:01:00 starts crawling
    @Scheduled(cron = "00 50 13 * * *")
//    @GetMapping
    public void scheduler() {
        Runnable runnable = null;

        for (int i = 0; i < 20; i++) {
            runnable = new MultiCrawler(i * 500, i);
            Thread t = new Thread(runnable);
            t.start();
        }
        runnable = new BeefCrawler();
        Thread t= new Thread(runnable);
        t.start();

    }

}


