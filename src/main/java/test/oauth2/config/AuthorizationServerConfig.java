package test.oauth2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import test.oauth2.service.MemberService;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor    // private final 정의 필드들을 생성자로 의존성 주입
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    private final ClientDetailsService clientDetailsService;
    private final JwtProperties jwtProperties;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                 .userDetailsService(memberService)
                .tokenStore(tokenStore())
                .accessTokenConverter(jwtAccessTokenConverter());
    }

    @Bean
    public TokenStore tokenStore() {
        return new CustomJwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    JwtAccessTokenConverter jwtAccessTokenConverter() {
        CustomJwtTokenConverter customJwtTokenConverter = new CustomJwtTokenConverter(jwtProperties, clientDetailsService);
        customJwtTokenConverter.setSigningKey(jwtProperties.getJWT_SIGNING_KEY());
        return customJwtTokenConverter;
    }

    /* client 정보를 Database 에서 가져오는 설정 */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }

}
