package test.oauth2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ClientDetailsService clientDetailsService;
    private final CustomBearerTokenExtractor customBearerTokenExtractor;
    private final JwtProperties jwtProperties;

    /* AuthenticationManager Bean 등록 */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /* passwordEncoder Bean 등록*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();  // 비밀번호 앞에 중괄호({})안에 지정된 값으로 암복호화
    }

    /* JWT 토큰형식으로 사용 Bean 등록 */
    @Bean
    public TokenStore tokenStore() {
        CustomJwtTokenStore tokenStore = new CustomJwtTokenStore(jwtAccessTokenConverter());    // JwtAccessTokenConverter 의존관계 주입
        tokenStore.setTokenExtractor(customBearerTokenExtractor);
        return tokenStore;
    }

    /* JWT 토큰 해석 Bean 등록 */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        CustomJwtTokenConverter customJwtTokenConverter = new CustomJwtTokenConverter(jwtProperties, clientDetailsService);
        customJwtTokenConverter.setSigningKey(jwtProperties.getJWT_SIGNING_KEY());
        return customJwtTokenConverter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
            .and()
            .csrf().disable()
            .anonymous().disable()
            .authorizeRequests().anyRequest().authenticated();
    }
}
