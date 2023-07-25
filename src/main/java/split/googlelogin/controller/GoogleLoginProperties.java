package split.googlelogin.controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class GoogleLoginProperties {

    @Value("${google.clientId}")
    private String clientId;

    @Value("${google.clientSecret}")
    private String clientSecret;

    @Value("${google.redirectUri}")
    private String redirectUri;
}
