package test.oauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.oauth2.entity.Member;

public interface MemberRepository  extends JpaRepository<Member, String> {
}
