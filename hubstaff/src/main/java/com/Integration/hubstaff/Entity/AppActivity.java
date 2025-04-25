package com.Integration.hubstaff.Entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "AppActivity")
public class AppActivity {

    @DynamoDBHashKey(attributeName = "activityId")
    private Long activityId;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "app_user-Index", attributeName = "applicationName")
    private String applicationName;

    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "app_user-Index", attributeName = "userId")
    private Integer userId;

    @DynamoDBAttribute(attributeName = "organizationId")
    private Integer organizationId;

    @DynamoDBAttribute(attributeName = "activityDate")
    private String activityDate;

    @DynamoDBAttribute( attributeName = "createdAt")
    private String createdAt;

    @DynamoDBAttribute( attributeName = "updatedIndex")
    private String updatedAt;

    @DynamoDBAttribute(attributeName = "tracked")
    private Integer tracked;


}
