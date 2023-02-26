package com.afilm.wedding.repository;

import com.afilm.wedding.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {

    Item findItemById(int id);
  //  Page<Item> findByNameContaining(String searchKeyword, Pageable pageable);
}
