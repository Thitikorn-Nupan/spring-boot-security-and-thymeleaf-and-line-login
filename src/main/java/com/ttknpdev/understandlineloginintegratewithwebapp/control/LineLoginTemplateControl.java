package com.ttknpdev.understandlineloginintegratewithwebapp.control;

import com.ttknpdev.understandlineloginintegratewithwebapp.log.Logback;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/api")
public class LineLoginTemplateControl {

    private final Logback logback;

    public LineLoginTemplateControl() {
        logback = new Logback(LineLoginTemplateControl.class);
    }


    @GetMapping("/hello-world")
    public String helloWordPage(Model model) {
        model.addAttribute("message", "Hello World");
        return "hello-word";
    }

    @GetMapping("/app/profile")
    public String indexPage(Model model,
                        @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                        @AuthenticationPrincipal OAuth2User oauth2User) {
        // model can call in index.html
        model.addAttribute("userName", oauth2User.getName());
        model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
        model.addAttribute("userAttributes", oauth2User.getAttributes());
        logback.log.warn("*** OAuth2User ****\n" +
                        "oauth2User.getAttributes() : {}\n" +
                        "oauth2User.getName() : {}\n" +
                        "oauth2User.getAuthorities() : {}\n" +
                        "***********************"
                , oauth2User.getAttributes(),
                oauth2User.getName(),
                oauth2User.getAuthorities()
        );
        logback.log.warn("*** OAuth2AuthorizedClient ****\n" +
                        "authorizedClient.getAccessToken().getTokenType().getValue() : {}\n" +
                        "authorizedClient.getAccessToken().getScopes() : {}\n" +
                        "authorizedClient.getAccessToken().getTokenValue() : {}\n" +
                        "authorizedClient.getClientRegistration().getClientName() : {}\n" +
                        "***********************"
                , authorizedClient.getAccessToken().getTokenType().getValue(),
                authorizedClient.getAccessToken().getScopes(),
                authorizedClient.getAccessToken().getTokenValue(),
                authorizedClient.getClientRegistration().getClientName()
        );
        return "index";
    }

    @GetMapping("/app/info-oauth")
    public ResponseEntity<Map<String, Object>> infoOauth2(
            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
            @AuthenticationPrincipal OAuth2User oauth2User,
            Principal user) {

        logback.log.warn("user {}",user);
        Map<String, Object> map = new HashMap<>();
        map.put("authorizedClient", authorizedClient);
        map.put("oauth2User", oauth2User);
        return ResponseEntity.ok(map);
        /*
        "oauth2User": {
            "authorities": [
                {
                    "authority": "OAUTH2_USER",
                    "attributes": {
                        "userId": "**",
                        "displayName": "thitikorn🏜",
                        "statusMessage": "just keep practicing.",
                        "pictureUrl": "https://profile.line-scdn.net/0h50iEQaY8ah9EPH9RgcAUYDRsaXVnTTMNPwokfnI1Y3t7DHhKbA9xLXM5Znh_C35BaF4mKyM0NyxILx15WmqWK0MMNy54CyxJblgt_w"
                    }
                },
                {
                    "authority": "SCOPE_profile"
                }
            ],
            "attributes": {
                "userId": "**",
                "displayName": "thitikorn🏜",
                "statusMessage": "just keep practicing.",
                "pictureUrl": "https://profile.line-scdn.net/0h50iEQaY8ah9EPH9RgcAUYDRsaXVnTTMNPwokfnI1Y3t7DHhKbA9xLXM5Znh_C35BaF4mKyM0NyxILx15WmqWK0MMNy54CyxJblgt_w"
            },
            "name": "**"
        }
     ],
      "authorizedClient": {
        "clientRegistration": {
            "registrationId": "line",
            "clientId": "**",
            "clientSecret": "**",
            "clientAuthenticationMethod": {
                "value": "client_secret_post"
            },
            "authorizationGrantType": {
                "value": "authorization_code"
            },
            "redirectUri": "http://localhost:8080/login/oauth2/code/whatever",
            "scopes": [
                "profile",
                "OPENID_CONNECT",
                "OC_EMAIL"
            ],
            "providerDetails": {
                "authorizationUri": "https://access.line.me/oauth2/v2.1/authorize",
                "tokenUri": "https://api.line.me/oauth2/v2.1/token",
                "userInfoEndpoint": {
                    "uri": "https://api.line.me/v2/profile?scope=OPENID_CONNECT",
                    "authenticationMethod": {
                        "value": "header"
                    },
                    "userNameAttributeName": "userId"
                        },
                        "jwkSetUri": "https://api.line.me/oauth2/v2.1/verify",
                        "issuerUri": null,
                        "configurationMetadata": {}
                    },
                    "clientName": "LINE"
                },
                "principalName": "**",
                "accessToken": {
                    "tokenValue": "**",
                    "issuedAt": "2024-08-24T09:33:28.167863800Z",
                    "expiresAt": "2024-09-23T09:33:28.167863800Z",
                    "tokenType": {
                        "value": "Bearer"
                    },
                    "scopes": [
                        "profile"
                    ]
                },
                "refreshToken": {
                    "tokenValue": "**",
                    "issuedAt": "2024-08-24T09:33:28.167863800Z",
                    "expiresAt": null
                }
            }
        }

    */
    }
}
