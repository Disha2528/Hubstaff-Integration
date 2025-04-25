package com.Integration.hubstaff.Repository;

import com.Integration.hubstaff.Entity.User;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepo {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public User save(User user){
        dynamoDBMapper.save(user);
        return user;
    }


    public User getUserById(Integer userId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":v_userId", new AttributeValue().withN(userId.toString()));

        DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>()
                .withIndexName("userId-name-Index")
                .withConsistentRead(false)
                .withKeyConditionExpression("userId = :v_userId")
                .withExpressionAttributeValues(eav);

        List<User> userList = dynamoDBMapper.query(User.class, queryExpression);

        return userList.isEmpty() ? null : userList.get(0);
    }


    public List<User> getUserByOrganization(Integer organizationId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":v_organizationId", new AttributeValue().withN(organizationId.toString()));

        DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>()
                .withIndexName("organizationId-Index")
                .withConsistentRead(false)
                .withKeyConditionExpression("organizationId = :v_organizationId")
                .withExpressionAttributeValues(eav);

        List<User> userList = dynamoDBMapper.query(User.class, queryExpression);

        return userList;
    }
}
