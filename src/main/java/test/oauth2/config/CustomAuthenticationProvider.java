package test.oauth2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import test.oauth2.service.MemberService;

/* tbl_biz_member의 비밀번호 암호화 방식이 달라서 해당 passwordEncoder로 인증과정 재정의 */
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MemberService memberService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String bizId = authentication.getName();
        String bizPwd = authentication.getCredentials().toString();

        UserDetails findMember = memberService.loadUserByUsername(bizId);

        DelegatingPasswordEncoder delegatingPasswordEncoder
                = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(new CustomPasswordEncoder()); // SHA-256 (common과 다름)

        if(!delegatingPasswordEncoder.matches(bizPwd, findMember.getPassword())) {
            throw new BadCredentialsException("회원 비밀번호가 일치하지 않습니다. 다시 한 번 확인해주세요.");
        }

        return new UsernamePasswordAuthenticationToken(bizId, bizPwd, findMember.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
