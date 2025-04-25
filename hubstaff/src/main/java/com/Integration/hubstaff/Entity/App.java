package com.Integration.hubstaff.Entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "App")
public class App {

    @DynamoDBHashKey(attributeName = "applicationName")
    private String applicationName;

    @DynamoDBRangeKey(attributeName = "userId")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "userId-Index", attributeName = "userId")
    private Integer userId;

    @DynamoDBAttribute(attributeName = "organizationId")
    private Integer organizationId;
}
