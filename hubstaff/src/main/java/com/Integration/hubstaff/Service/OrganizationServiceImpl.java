package com.Integration.hubstaff.Service;

import com.Integration.hubstaff.Config.HubstaffConfig;
import com.Integration.hubstaff.DTO.OrgResponse;
import com.Integration.hubstaff.DTO.OrganizationDTO;
import com.Integration.hubstaff.Entity.Organization;
import com.Integration.hubstaff.Exception.BadRequestException;
import com.Integration.hubstaff.Repository.OrganizationRepo;
import com.Integration.hubstaff.Util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrganizationRepo organizationRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HubstaffConfig hubstaffConfig;

    @Override
    public void getNewOrgs() {
        String accessToken = oAuthService.getAccessToken();
        ValidationUtil.checkNotNullOrBlank(accessToken, "Access Token");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<OrgResponse> response = restTemplate.exchange(
                hubstaffConfig.getHubstaffBaseUrl() + hubstaffConfig.getOrgUrl(),
                HttpMethod.GET,
                requestEntity,
                OrgResponse.class
        );

        OrgResponse orgResponse = response.getBody();
        ValidationUtil.checkNotNull(orgResponse, "Organization response");

        List<OrganizationDTO> organizationList = orgResponse.getOrgList();
        if (ValidationUtil.isNullOrEmpty(organizationList)) {
            throw new BadRequestException("Organization list is empty");
        }

        for (OrganizationDTO organization : organizationList) {
            organizationRepo.save(modelMapper.map(organization, Organization.class));
        }
    }


    @Override
    public OrgResponse getOrgList(){
        List<Organization> organizations= organizationRepo.getOrganizations();
        return new OrgResponse(organizations.stream().map(organization -> modelMapper.map(organization, OrganizationDTO.class)).toList());

    }

    @Override
    public OrganizationDTO getOrg(Integer organizationId) {
        ValidationUtil.checkNotNull(organizationId, "Organization ID");

        Organization organization = organizationRepo.getOrg(organizationId);
        ValidationUtil.checkNotNull(organization, "Organization not found");

        return modelMapper.map(organization, OrganizationDTO.class);
    }

}
