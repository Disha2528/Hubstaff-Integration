package com.Integration.hubstaff.Scheduler;

import com.Integration.hubstaff.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserScheduler {

    @Autowired
    private UserService userService;

    @Scheduled(cron = "0 0 0 * * *")
    public void saveUsers(){
        userService.getUsers();
    }
}
