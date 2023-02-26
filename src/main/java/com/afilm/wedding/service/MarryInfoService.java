package com.afilm.wedding.service;

import com.afilm.wedding.domain.MarryInfo;
import com.afilm.wedding.dto.MarryInfoDto;
import com.afilm.wedding.repository.MarryInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class MarryInfoService {

    //MarryInfoRepository 객체 생성
    private MarryInfoRepository marryInfoRepository;


    // Entity -> Dto로 변환
    private MarryInfoDto convertEntityToDto(MarryInfo marryInfo) {
        return MarryInfoDto.builder()
                .id(marryInfo.getId())
                .femaleName(marryInfo.getFemaleName())
                .maleName(marryInfo.getMaleName())
                .info_1(marryInfo.getInfo_1())
                .info_2(marryInfo.getInfo_2())
                .marryDate(marryInfo.getMarryDate())
                .build();
    }

    @Transactional
    public Long savePost(MarryInfoDto marryInfoDto) {
        return marryInfoRepository.save(marryInfoDto.toEntity()).getId();
    }

}
