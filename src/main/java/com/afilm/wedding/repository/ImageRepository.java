package com.afilm.wedding.repository;

import com.afilm.wedding.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {

    Image findItemById(int id);

    List<Image> findByUserId(Long id);
    //  Page<Item> findByNameContaining(String searchKeyword, Pageable pageable);
}
