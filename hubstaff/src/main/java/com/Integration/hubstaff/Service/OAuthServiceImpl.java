package com.Integration.hubstaff.Service;

import com.Integration.hubstaff.Config.HubstaffConfig;
import com.Integration.hubstaff.DTO.IntegrationDTO;
import com.Integration.hubstaff.DTO.TokenResponse;
import com.Integration.hubstaff.Entity.Integration;
import com.Integration.hubstaff.Exception.ApiException;
import com.Integration.hubstaff.Repository.IntegrationRepo;
import com.Integration.hubstaff.Util.ValidationUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class OAuthServiceImpl implements OAuthService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HubstaffConfig hubstaffConfig;

    @Autowired
    private IntegrationRepo integrationRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void getCode() {
        String redirect_uri = hubstaffConfig.getRedirectUri();
        String scope = "hubstaff:read";
        String url = hubstaffConfig.getAuthUrl();
        String finalUrl = url + "?client_id=" + hubstaffConfig.getClientId()
                + "&response_type=" + "code"
                + "&nonce=22343sdhihoinl"
                + "&redirect_uri=" + hubstaffConfig.getRedirectUri()
                + "&scope=" + scope;
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + finalUrl);
            } else if (os.contains("mac")) {
                Runtime.getRuntime().exec("open " + finalUrl);
            } else if (os.contains("nix") || os.contains("nux")) {
                Runtime.getRuntime().exec("xdg-open " + finalUrl);
            } else {
                System.out.println("Unsupported OS: " + os);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public TokenResponse exchangeCodeForToken(String code) {
        ValidationUtil.checkNotNullOrBlank(code, "Authorization Code");

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setBasicAuth(hubstaffConfig.getClientId(), hubstaffConfig.getClientSecret());

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "authorization_code");
            body.add("code", code);
            body.add("redirect_uri", hubstaffConfig.getRedirectUri());

            ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
                   hubstaffConfig.getTokenUrl(),
                    new HttpEntity<>(body, headers),
                    TokenResponse.class);

            saveTokens(response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException e) {
            String errorDetails = e.getResponseBodyAsString();
            throw new ApiException("error while exchanging code",500, null);
        } catch (Exception e) {
            throw new ApiException("Token exchange failed: " ,400,null);
        }
    }

    @Override
    public void saveTokens(TokenResponse tokenResponse) {
        ValidationUtil.checkNotNull(tokenResponse, "Token Response");
        long currentTime = System.currentTimeMillis();
        Date now = new Date();

        IntegrationDTO integrationDTO= new IntegrationDTO();
        integrationDTO.setClientId(hubstaffConfig.getClientId());
        integrationDTO.setAccessToken(tokenResponse.getAccessToken());
        integrationDTO.setRefreshToken(tokenResponse.getRefreshToken());
        integrationDTO.setTokenExpiry(String.valueOf(currentTime + (tokenResponse.getExpiresIn() * 1000)));
        integrationDTO.setCreatedAt(now.toString());
        integrationDTO.setUpdatedAt(now.toString());
        integrationDTO.setLastSyncedAt(now.toString());
        integrationDTO.setStatus("ACTIVE");
        integrationDTO.setClientSecret(hubstaffConfig.getClientSecret());

        integrationRepo.save(modelMapper.map(integrationDTO, Integration.class));
    }

    @Override
    public String getAccessToken() {
        Integration integration = integrationRepo.getIntegration(hubstaffConfig.getClientId());
        ValidationUtil.checkNotNull(integration, "Integration");

        IntegrationDTO integrationDTO = modelMapper.map(integration, IntegrationDTO.class);
        ValidationUtil.checkNotNullOrBlank(integrationDTO.getAccessToken(), "accessToken");

        return integrationDTO.getAccessToken();
    }

    @Override
    public void refreshToken() {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setBasicAuth(hubstaffConfig.getClientId(), hubstaffConfig.getClientSecret());

            Integration integration = integrationRepo.getIntegration(hubstaffConfig.getClientId());
            if(integration==null) return;

            IntegrationDTO integrationDTO = modelMapper.map(integration, IntegrationDTO.class);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("grant_type", "refresh_token");
            map.add("refresh_token", integrationDTO.getRefreshToken());

            ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
                    hubstaffConfig.getTokenUrl(),
                    new HttpEntity<>(map, headers),
                    TokenResponse.class
            );

            // Save the new token
            saveTokens(response.getBody());

        } catch (HttpClientErrorException e) {
            log.error("Authorization error", e);
            throw new ApiException("Could not fetch refresh token", 400, e);
        } catch (Exception e) {
            log.error("Unexpected error during token refresh", e);
            throw new RuntimeException("Token refresh failed", e);
        }
    }

}
