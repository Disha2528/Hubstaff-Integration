package com.Integration.hubstaff.Config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class HubstaffConfig {

    @Value("${base.url}")
    private String hubstaffBaseUrl;

    @Value("${client.id}")
    private String clientId;

    @Value("${client.secret}")
    private String clientSecret;

    @Value("${redirect.uri}")
    private String redirectUri;

    @Value("${authorization.code.url}")
    private String authUrl;

    @Value("${authorization.token.url}")
    private String tokenUrl;

    @Value("${org.url}")
    private String orgUrl;

    @Value("${users.url}")
    private String userUrl;

}
