package com.Integration.hubstaff.Entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "Organization")
public class Organization {

    @DynamoDBHashKey(attributeName = "organizationId")
    private Integer organizationId;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "organizationName-Index",attributeName = "organizationName")
    private String organizationName;

    @DynamoDBAttribute(attributeName = "status")
    private String status;

    @DynamoDBAttribute(attributeName = "createdAt")
    private String createdAt;

    @DynamoDBAttribute( attributeName = "updatedAt")
    private String updatedAt;

    @DynamoDBAttribute(attributeName = "inviteUrl")
    private String inviteUrl;

}
