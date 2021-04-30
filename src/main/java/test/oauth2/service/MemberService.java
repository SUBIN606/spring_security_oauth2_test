package test.oauth2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import test.oauth2.dto.MemberDto;
import test.oauth2.entity.Member;
import test.oauth2.repository.MemberRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member findMember = repository.findById(username).orElse(null);
        return new User(findMember.getBizid(), findMember.getApi_pwd(), getAuthorities(findMember));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Member member) {
        return Arrays.asList(new SimpleGrantedAuthority(getUserRole(member.getLevel())));
    }

    private String getUserRole(String level) {
       if(level.equals("MS")){
           return "ADMIN";
       }
        return "USER";
    }
}
