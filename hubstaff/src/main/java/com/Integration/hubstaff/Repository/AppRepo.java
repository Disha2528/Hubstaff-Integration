package com.Integration.hubstaff.Repository;

import com.Integration.hubstaff.Entity.App;
import com.Integration.hubstaff.Entity.AppActivity;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AppRepo {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public void save(App app){
        dynamoDBMapper.save(app);
    }

    //user uses which apps
    //List of apps
    public List<App> getApps(Integer userId){
        Map<String, AttributeValue> eav= new HashMap<>();
        eav.put(":userId", new AttributeValue().withN(userId.toString()));

        DynamoDBQueryExpression<App> query = new DynamoDBQueryExpression<App>()
                .withIndexName("userId-Index")
                .withConsistentRead(false)
                .withKeyConditionExpression("userId =:userId")
                .withExpressionAttributeValues(eav);

        return dynamoDBMapper.query(App.class, query);
    }
}
