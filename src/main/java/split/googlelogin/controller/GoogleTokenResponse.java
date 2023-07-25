package split.googlelogin.controller;

import lombok.Getter;

@Getter
public class GoogleTokenResponse {

    private String access_token;
    private Integer expires_in;
    private String scope;
    private String token_type;
    private String id_token;
}
