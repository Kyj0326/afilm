package com.afilm.wedding.domain;


import com.afilm.security.model.User;
import lombok.*;
import sun.rmi.runtime.Log;

import javax.persistence.*;

@NoArgsConstructor  // 외부에서의 생성을 열어 둘 필요가 없을 때 / 보안적으로 권장된다.
@Setter
@Getter
@Entity
@Builder
public class Image {

    @Id // PK Field
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // PK의 생성 규칙
    @Column(name = "img_id")
    private Long id;

    private String imgName;

    private String imgPath;

    private int seq;

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "user_id" )
    private User user;

    @Builder
    public Image(Long id, String imgName, String imgPath, Integer seq, User user) {
        this.id = id;
        this.imgName = imgName;
        this.imgPath = imgPath;
        this.seq = seq;
        this.user = user;
    }


}
