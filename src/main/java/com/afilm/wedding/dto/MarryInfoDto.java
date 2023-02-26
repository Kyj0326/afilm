package com.afilm.wedding.dto;

import com.afilm.wedding.domain.Board;
import com.afilm.wedding.domain.MarryInfo;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Date;

public class MarryInfoDto {

    private Long id;
    private String maleName;
    private String femaleName;
    private String info_1;
    private String info_2;
    private LocalDateTime marryDate;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public MarryInfo toEntity(){
        MarryInfo marryInfo = MarryInfo.builder()
                .id(id)
                .maleName(maleName)
                .femaleName(femaleName)
                .info_1(info_1)
                .info_2(info_2)
                .marryDate(marryDate)
                .build();
        return marryInfo;
    }

    //
    @Builder
    public MarryInfoDto(Long id, String maleName, String femaleName, String info_1, String info_2, LocalDateTime marryDate, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.maleName = maleName;
        this.femaleName = femaleName;
        this.info_1 = info_1;
        this.info_2 = info_2;
        this.marryDate = marryDate;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
