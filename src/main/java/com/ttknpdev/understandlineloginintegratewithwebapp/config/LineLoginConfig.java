package com.ttknpdev.understandlineloginintegratewithwebapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Configuration
public class LineLoginConfig {

    private final String clientId;
    private final String clientSecret;
    private final String pathRedirect;

    // work for import like key classpath as spring.config.import=classpath:<Path> another case won work
    public LineLoginConfig(@Value("${CLIENT.ID}") String clientId, @Value("${CLIENT.SECRET.ID}") String clientSecret, @Value("${PATH.REDIRECT}") String pathRedirect) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.pathRedirect = pathRedirect;
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.lineClientRegistration());
    }

    /**
        You can use Spring Boot Auto-configuration set all detail on application.yml, application.properties
    */
    private ClientRegistration lineClientRegistration() {
        return ClientRegistration.withRegistrationId("line")
                .clientId(clientId) // Using LINE Login Channel ID
                .clientSecret(clientSecret) // Using LINE Login Channel Secret
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                // same call back
                .redirectUri(pathRedirect)
                .scope("profile") // not worked
                // .scope("profile","OPENID_CONNECT","OC_EMAIL") // not worked
                .authorizationUri("https://access.line.me/oauth2/v2.1/authorize") // redirect to url line
                .tokenUri("https://api.line.me/oauth2/v2.1/token")
                .jwkSetUri("https://api.line.me/oauth2/v2.1/verify")
                .userNameAttributeName("userId")
                .userInfoUri("https://api.line.me/v2/profile") // Use take infor  of User by access token กับ Social API
                .clientName("LINE")
                .build();
    }
}
