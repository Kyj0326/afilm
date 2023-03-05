package com.afilm.wedding.repository;

import com.afilm.wedding.domain.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Images,Long> {

    Images findItemById(int id);
  //  Page<Item> findByNameContaining(String searchKeyword, Pageable pageable);
}
