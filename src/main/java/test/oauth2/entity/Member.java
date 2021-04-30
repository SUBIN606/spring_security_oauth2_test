package test.oauth2.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_biz_member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본생성자를 추가하며, JPA 가 생성하는 것만 허용
public class Member {

    @Column(name = "biz_name")
    private String name;

    @Column(name="biz_level")
    private String level;

    @Id
    private String bizid;

    private String bizpwd;
    private String biz_manager;
    private String api_pwd;

    @Builder
    public Member(String name, String level, String bizid, String bizpwd, String biz_manager, String api_pwd) {
        this.name = name;
        this.level = level;
        this.bizid = bizid;
        this.bizpwd = bizpwd;
        this.biz_manager = biz_manager;
        this.api_pwd = api_pwd;
    }
}
