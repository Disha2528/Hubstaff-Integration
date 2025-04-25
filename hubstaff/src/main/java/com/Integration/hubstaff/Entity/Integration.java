package com.Integration.hubstaff.Entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "Integration")
public class Integration {


    @DynamoDBHashKey(attributeName = "clientId")
    private String clientId;

    @DynamoDBAttribute(attributeName = "clientSecret")
    private String clientSecret;

    @DynamoDBAttribute(attributeName = "accessToken")
    private String accessToken;

    @DynamoDBAttribute(attributeName = "refreshToken")
    private String refreshToken;

    @DynamoDBAttribute(attributeName = "tokenExpiry")
    private String tokenExpiry;

    @DynamoDBAttribute(attributeName = "status")
    private String status;

    @DynamoDBAttribute(attributeName = "lastSyncedAt")
    private String lastSyncedAt;

    @DynamoDBAttribute(attributeName = "createdAt")
    private String createdAt;

    @DynamoDBAttribute(attributeName = "updatedAt")
    private String updatedAt;
}
