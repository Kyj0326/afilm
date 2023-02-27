package com.afilm.security.model;

import com.afilm.wedding.domain.MarryInfo;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity // jpa가 관리하는 것
@Data
@Getter
@Setter
@NoArgsConstructor  // 기본 생성자만 생성
public class User {
    @Id //pk를 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
    private String provider;
    private String providerId;
    @CreationTimestamp  // 현재 시간으로 쿼리를 생성
    private Timestamp createDate;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MarryInfo marryInfo;

    public void setMarryInfo(MarryInfo marryInfo) {
        this.marryInfo = marryInfo;
        marryInfo.setUser(this);
    }

    @Builder  // 생성자 호출 쉽게 하기 위함(가독성)
    public User(String username, String password, String email, String role, String provider, String providerId, Timestamp createDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
    }
}
