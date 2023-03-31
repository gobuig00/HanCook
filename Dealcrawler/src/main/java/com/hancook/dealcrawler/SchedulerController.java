package com.hancook.dealcrawler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
//@RequestMapping("/")
public class SchedulerController {

    // Everyday 00:01:00 starts crawling
    @Scheduled(cron = "00 00 12 * * *")
//    @GetMapping
    public void scheduler() {
        Runnable runnable = null;
//        for(int i=0;i<2;i++){
//            runnable = new MultiCrawler(i*20,i);
//            Thread t = new Thread(runnable);
//            t.start();
//        }


        for (int i = 0; i < 20; i++) {
            runnable = new MultiCrawler(i * 500, i);
            Thread t = new Thread(runnable);
            t.start();
        }
    }
    //@Scheduled(cron = "00 00 14 * * *")
    @GetMapping
    public void beef(){
       Runnable runnable = new BeefCrawler();
       Thread t= new Thread(runnable);
       t.start();
    }

}


