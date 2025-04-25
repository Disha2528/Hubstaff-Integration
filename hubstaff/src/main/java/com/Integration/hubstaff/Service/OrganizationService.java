package com.Integration.hubstaff.Service;

import com.Integration.hubstaff.DTO.OrgResponse;
import com.Integration.hubstaff.DTO.OrganizationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrganizationService {

    public void getNewOrgs();

    public OrgResponse getOrgList();

    public OrganizationDTO getOrg(Integer organizationId);
}
