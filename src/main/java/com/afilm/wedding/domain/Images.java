package com.afilm.wedding.domain;


import com.afilm.security.model.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 외부에서의 생성을 열어 둘 필요가 없을 때 / 보안적으로 권장된다.
@Setter
@Getter
@Entity
public class Images {

    @Id // PK Field
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // PK의 생성 규칙
    @Column(name = "img_id")
    private Long id;

    private String imgName;

    private String imgPath;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "user_id" )
    private User user;

}
