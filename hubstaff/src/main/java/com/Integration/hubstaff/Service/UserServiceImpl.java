package com.Integration.hubstaff.Service;

import com.Integration.hubstaff.Config.HubstaffConfig;
import com.Integration.hubstaff.DTO.OrganizationDTO;
import com.Integration.hubstaff.DTO.UserDTO;
import com.Integration.hubstaff.DTO.UserResponse;
import com.Integration.hubstaff.Entity.User;
import com.Integration.hubstaff.Exception.BadRequestException;
import com.Integration.hubstaff.Exception.EntityNotFoundException;
import com.Integration.hubstaff.Repository.UserRepo;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HubstaffConfig hubstaffConfig;

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrganizationService organizationService;

    @Override
    public UserResponse getUsers() {
        List<OrganizationDTO> organizationDTOList = organizationService.getOrgList().getOrgList();
        ValidationUtil.checkNotNullOrBlank(oAuthService.getAccessToken(), "Access Token");
        if (ValidationUtil.isNullOrEmpty(organizationDTOList)) {
            throw new BadRequestException("Organization list is empty. Cannot fetch users.");
        }

        List<UserDTO> userDTOList = new ArrayList<>();
        String token = oAuthService.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);

        for (OrganizationDTO organizationDTO : organizationDTOList) {
            Integer orgId = organizationDTO.getOrganizationId();
            ValidationUtil.checkNotNull(orgId, "Organization ID");

            ResponseEntity<UserResponse> response = restTemplate.exchange(
                    hubstaffConfig.getHubstaffBaseUrl() + hubstaffConfig.getOrgUrl() + "/" + orgId + "/" + hubstaffConfig.getUserUrl(),
                    HttpMethod.GET,
                    requestEntity,
                    UserResponse.class
            );

            List<UserDTO> users = response.getBody().getUserList();
            if (!ValidationUtil.isNullOrEmpty(users)) {
                for (UserDTO userDTO : users) {
                    userDTO.setOrganizationId(organizationDTO.getOrganizationId());
                    userRepo.save(modelMapper.map(userDTO, User.class));
                }
                userDTOList.addAll(users);
            }
        }
        return new UserResponse(userDTOList);
    }


    @Override
    public UserDTO getUSerById(Integer userId) {
        ValidationUtil.checkNotNull(userId, "User ID");
        User user = userRepo.getUserById(userId);
        ValidationUtil.checkNotNull(user, "User not found with ID: " + userId);
        return modelMapper.map(user, UserDTO.class);
    }


    @Override
    public UserResponse getUsersByOrganization(Integer organizationId) throws EntityNotFoundException {
        ValidationUtil.checkNotNull(organizationId, "Organization ID");
        OrganizationDTO org = organizationService.getOrg(organizationId);
        if (org == null) {
            throw new EntityNotFoundException("Organization not found with ID: " + organizationId);
        }

        List<UserDTO> userDTOList = userRepo.getUserByOrganization(organizationId)
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();

        return new UserResponse(userDTOList);
    }



}
