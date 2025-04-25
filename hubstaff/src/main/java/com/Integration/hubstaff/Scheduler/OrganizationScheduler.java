package com.Integration.hubstaff.Scheduler;

import com.Integration.hubstaff.Service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrganizationScheduler {

    @Autowired
    private OrganizationService organizationService;

    @Scheduled(cron = "0 0 0 * * *")
    public void fetchOrgsDaily(){
        organizationService.getNewOrgs();
    }
}
