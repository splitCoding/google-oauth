package split.googlelogin.controller;

import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class GoogleLoginController {

    private static final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";

    private final GoogleLoginProperties properties;

    public GoogleLoginController(final GoogleLoginProperties properties) {
        this.properties = properties;
    }

    @GetMapping("/login")
    public String googleLogin() {
        final String url = "https://accounts.google.com/o/oauth2/v2/auth?"
            + "client_id=" + properties.getClientId()
            + "&redirect_uri=" + properties.getRedirectUri()
            + "&response_type=code"
            + "&scope=profile";

        return "redirect:" + url;
    }  //구글 로그인 화면으로 리다이렉트 해주는 메서드

    @GetMapping("/oauth2callback")
    @ResponseBody
    public String showCode(@RequestParam(value = "code") String code) {
        return getToken(code);
    }  //리다이렉트 URI에 존재하는 코드로 토큰을 발급받아 화면에 띄워주는 메서드

    private String getToken(final String code) {
        final RestTemplate restTemplate = new RestTemplate();

        final ResponseEntity<GoogleTokenResponse> response = restTemplate.postForEntity(
            GOOGLE_TOKEN_URL,
            getParamsForTokenRequest(code),
            GoogleTokenResponse.class
        );

        if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
            return response.getBody().getAccess_token();
        }
        return "FAIL TO GET TOKEN";
    }  //로그인 성공시 반환받은 코드를 통해 POST 요청으로 토큰을 발급 받는 메서드

    private HashMap<String, String> getParamsForTokenRequest(final String code) {
        final HashMap<String, String> params = new HashMap<>();

        params.put("code", code);
        params.put("client_id", properties.getClientId());
        params.put("client_secret", properties.getClientSecret());
        params.put("redirect_uri", properties.getRedirectUri());
        params.put("grant_type", "authorization_code");

        return params;
    }  //토큰 발급 요청에 필요한 파라미터를 담은 Map을 반환해주는
}
