package com.Integration.hubstaff.Scheduler;

import com.Integration.hubstaff.Config.HubstaffConfig;
import com.Integration.hubstaff.Service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TokenScheduler {

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private HubstaffConfig hubstaffConfig;

    @Scheduled(fixedRate = 60*60*1000)
    public void  getNewToken(){
        oAuthService.refreshToken();
    }
}
