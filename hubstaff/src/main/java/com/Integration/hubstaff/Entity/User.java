package com.Integration.hubstaff.Entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "User")
public class User {

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "userId-name-Index", attributeName = "userId")
    private Integer userId;

    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "userId-name-Index", attributeName = "userName")
    private String userName;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "organizationId-Index", attributeName = "organizationId")
    private Integer organizationId;

    @DynamoDBAttribute(attributeName = "firstName")
    private String firstName;

    @DynamoDBAttribute(attributeName = "lastName")
    private String lastName;

    @DynamoDBHashKey(attributeName = "email")
    private String email;

    @DynamoDBAttribute(attributeName = "status")
    private String status;

    @DynamoDBAttribute(attributeName = "timeZone")
    private String timeZone;

    @DynamoDBAttribute( attributeName = "createdAt")
    private String createdAt;

    @DynamoDBAttribute( attributeName = "updatedAt")
    private String updatedAt;

}
