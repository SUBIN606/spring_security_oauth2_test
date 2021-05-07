package test.oauth2.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test.oauth2.entity.Member;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {

    private String name;
    private String level;
    private String bizid;
    private String bizpwd;
    private String biz_manager;
  //  private String api_pwd;

    /* Entity 반환 */
    public Member toEntity() {
        return Member.builder()
                .name(name)
                .level(level)
                .bizid(bizid)
                .bizpwd(bizpwd)
                .biz_manager(biz_manager)
//                .api_pwd(api_pwd)
                .build();
    }
}
