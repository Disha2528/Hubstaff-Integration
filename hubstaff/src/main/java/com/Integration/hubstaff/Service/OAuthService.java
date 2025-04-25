package com.Integration.hubstaff.Service;

import com.Integration.hubstaff.DTO.IntegrationDTO;
import com.Integration.hubstaff.DTO.TokenResponse;
import io.jsonwebtoken.io.IOException;

import java.util.Map;

public interface OAuthService {

    public void getCode();

    public TokenResponse exchangeCodeForToken(String code);

    public void saveTokens(TokenResponse tokenResponse);

    public String getAccessToken();

    public void refreshToken();
}
