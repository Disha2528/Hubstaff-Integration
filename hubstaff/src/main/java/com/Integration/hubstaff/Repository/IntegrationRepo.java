package com.Integration.hubstaff.Repository;

import com.Integration.hubstaff.Entity.Integration;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class IntegrationRepo {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public void save(Integration integration){
        dynamoDBMapper.save(integration);
    }

    public Integration getIntegration(String clientId){
        Integration integration =dynamoDBMapper.load(Integration.class, clientId);
        return integration;
    }

}
