package com.Integration.hubstaff.Scheduler;

import com.Integration.hubstaff.Service.AppActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AppActivityScheduler {

    @Autowired
    private AppActivityService appActivityService;

    @Scheduled(cron = "0 0 0 * * *")
    public void getNewApps(){
        appActivityService.getNewAppActivity();
    }
}
