package com.afilm.wedding.service;

import com.afilm.security.model.User;
import com.afilm.security.repository.UserRepository;
import com.afilm.wedding.domain.MarryInfo;
import com.afilm.wedding.dto.MarryInfoDto;
import com.afilm.wedding.repository.MarryInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
public class MarryInfoService {

    //MarryInfoRepository 객체 생성
    private MarryInfoRepository marryInfoRepository;
    private UserRepository userRepository;


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
    public void savePost(MarryInfoDto marryInfoDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        user.setMarryInfo(marryInfoDto.toEntity());
        //return marryInfoRepository.save(marryInfoDto.toEntity()).getId();
    }

}
