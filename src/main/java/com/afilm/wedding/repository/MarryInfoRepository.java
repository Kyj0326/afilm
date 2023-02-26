package com.afilm.wedding.repository;


import com.afilm.wedding.domain.MarryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarryInfoRepository extends JpaRepository<MarryInfo, Long> {
}
