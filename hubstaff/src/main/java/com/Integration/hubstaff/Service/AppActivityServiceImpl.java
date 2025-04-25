package com.Integration.hubstaff.Service;

import com.Integration.hubstaff.Config.HubstaffConfig;
import com.Integration.hubstaff.DTO.*;
import com.Integration.hubstaff.Entity.App;
import com.Integration.hubstaff.Entity.AppActivity;
import com.Integration.hubstaff.Exception.ApiException;
import com.Integration.hubstaff.Exception.EntityNotFoundException;
import com.Integration.hubstaff.Repository.AppActivityRepo;
import com.Integration.hubstaff.Repository.AppRepo;
import com.Integration.hubstaff.Util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
@Service
public class AppActivityServiceImpl implements AppActivityService {

    @Autowired
    private AppActivityRepo appActivityRepo;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppRepo appRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private HubstaffConfig hubstaffConfig;


    @Override
    public void getNewAppActivity() {
        List<OrganizationDTO> organizationDTOList = organizationService.getOrgList().getOrgList();
        ValidationUtil.checkNotNull(organizationDTOList, "Organization list");

        String token = oAuthService.getAccessToken();
        ValidationUtil.checkNotNullOrBlank(token, "Access Token");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);

        String startDate = LocalDate.now().minusDays(11).toString();
        String endDate = LocalDate.now().toString();

        try {
            for (OrganizationDTO organizationDTO : organizationDTOList) {
                Integer orgId = organizationDTO.getOrganizationId();

                ResponseEntity<AppActivityResponse> response = restTemplate.exchange(
                        hubstaffConfig.getHubstaffBaseUrl() + hubstaffConfig.getOrgUrl() + "/" + orgId + "/application_activities/daily" +
                                "?date[start]=" + startDate + "&date[stop]=" + endDate,
                        HttpMethod.GET,
                        requestEntity,
                        AppActivityResponse.class
                );

                List<AppActivityDTO> appActivityDTOList = response.getBody().getActivityDTOList();

                for (AppActivityDTO appActivityDTO : appActivityDTOList) {
                    appActivityDTO.setOrganizationId(orgId);

                    AppDTO appDTO = new AppDTO();
                    appDTO.setApplicationName(appActivityDTO.getApplicationName());
                    appDTO.setUserId(appActivityDTO.getUserId());
                    appDTO.setOrganizationId(orgId);

                    appRepo.save(modelMapper.map(appDTO, App.class));
                    appActivityRepo.save(modelMapper.map(appActivityDTO, AppActivity.class));
                }
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ApiException("Hubstaff API error: " + e.getStatusText(), e.getStatusCode().value(), e);
        } catch (ResourceAccessException e) {
            throw new ApiException("Failed to connect to Hubstaff API", 503, e);
        }
    }

    @Override
    public ActivityDTO timeSpent(AppActivityDTO appActivityDTO) throws EntityNotFoundException {
        ValidationUtil.validateDto(appActivityDTO);
        ValidationUtil.checkNotNullOrBlank(appActivityDTO.getApplicationName(), "Application Name");
        ValidationUtil.checkNotNull(appActivityDTO.getUserId(), "User ID");

        List<AppActivity> appActivities = appActivityRepo.getAppActivityByAppNameAndUser(
                appActivityDTO.getApplicationName(),
                appActivityDTO.getUserId()
        );

         Integer time = appActivities.stream()
                .map(AppActivity::getTracked)
                .reduce(0, Integer::sum);

         return new ActivityDTO(appActivityDTO.getApplicationName(), time);
    }

    @Override
    public List<AppDTO> getApps(Integer userId) throws EntityNotFoundException {
        ValidationUtil.checkNotNull(userId, "User ID");

        UserDTO userDTO = userService.getUSerById(userId);
        ValidationUtil.checkNotNull(userDTO, "User");

        return appRepo.getApps(userId).stream()
                .map(app -> modelMapper.map(app, AppDTO.class))
                .toList();
    }
}
