package test.oauth2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final DataSource dataSource;
  //  private final PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
//    }

    /* inMemory 방식*/
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("clientId")                              // 클라이언트 아이디
                .secret("{noop}clientSecret")                               // 클라이언트 시크릿
                .redirectUris("http://localhost:8081/oauth2/callback")      // 인증 결과를 수신할 URI
                .authorizedGrantTypes("password", "refresh")                // 인증 방식
                .scopes("read", "write")                                    // 해당 클라이언트의 접근 범위
                .accessTokenValiditySeconds(60 * 60 * 4)                    // access token 유효 기간 (초 단위)
                .refreshTokenValiditySeconds(60 * 60 * 24 * 120)            // refresh token 유효 기간 (초 단위)
                .autoApprove(true);                                         // OAuth Approval 화면 나오지 않게 처리
    }
}
