package com.Integration.hubstaff.Repository;

import com.Integration.hubstaff.Entity.Organization;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrganizationRepo {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public void save(Organization organization){
        dynamoDBMapper.save(organization);
    }

    public List<Organization> getOrganizations(){
        List<Organization> organizationList= dynamoDBMapper.scan(Organization.class, new DynamoDBScanExpression());
        return organizationList;
    }

    public Organization getOrg(Integer organizationId){
        Organization organization= dynamoDBMapper.load(Organization.class, organizationId);
        return organization;
    }


}
