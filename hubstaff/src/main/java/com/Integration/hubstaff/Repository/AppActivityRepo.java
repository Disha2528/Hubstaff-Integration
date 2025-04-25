package com.Integration.hubstaff.Repository;

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
public class AppActivityRepo {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public void save(AppActivity appActivity){
        dynamoDBMapper.save(appActivity);
    }

    //get app activity
    public List<AppActivity> getAppActivityByAppNameAndUser(String appName, Integer userId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":appName", new AttributeValue().withS(appName));
        eav.put(":userId", new AttributeValue().withN(userId.toString()));

        DynamoDBQueryExpression<AppActivity> query = new DynamoDBQueryExpression<AppActivity>()
                .withIndexName("app_user-Index")
                .withConsistentRead(false)
                .withKeyConditionExpression("applicationName = :appName and userId = :userId")
                .withExpressionAttributeValues(eav);

        return dynamoDBMapper.query(AppActivity.class, query);
    }



}
