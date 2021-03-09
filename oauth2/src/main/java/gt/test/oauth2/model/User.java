package gt.test.oauth2.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@ToString
public class User  {
    private String id;
    private String name;
    private String password;
    private String authorities;


}
