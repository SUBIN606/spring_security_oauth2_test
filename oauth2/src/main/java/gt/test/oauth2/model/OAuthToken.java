package gt.test.oauth2.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OAuthToken {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private long expires_in;
    private String scope;
}
