package com.afilm.wedding.domain;

import com.afilm.security.model.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 외부에서의 생성을 열어 둘 필요가 없을 때 / 보안적으로 권장된다.
@Setter
@Getter
@Entity
public class MarryInfo {

    @Id // PK Field
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // PK의 생성 규칙
    @Column(name = "marryInfo_id")
    private Long id;

    private String maleName;
    private String femaleName;
    private String info_1;
    private String info_2;
    private LocalDateTime marryDate;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn( name = "member_id" )
//    private User user;

    @Builder
    public MarryInfo(Long id, String maleName, String femaleName, String info_1, String info_2, LocalDateTime marryDate, User user) {
        this.id = id;
        this.maleName = maleName;
        this.femaleName = femaleName;
        this.info_1 = info_1;
        this.info_2 = info_2;
        this.marryDate = marryDate;
       // this.user = user;
    }

//    public void addUser(User users){
//
//        user.setUser();
//    }
}
