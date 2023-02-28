package com.afilm.wedding.service;


import com.afilm.security.model.User;
import com.afilm.security.repository.UserRepository;
import com.afilm.wedding.domain.Item;
import com.afilm.wedding.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ItemService {

    private ItemRepository itemRepository;
    private UserRepository userRepository;


    public void saveItem(Item item, MultipartFile imgFile, User user) throws Exception {


        //User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

        String oriImgName = imgFile.getOriginalFilename();
        String imgName = "";

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";

        // UUID 를 이용하여 파일명 새로 생성
        // UUID - 서로 다른 객체들을 구별하기 위한 클래스
        UUID uuid = UUID.randomUUID();
        System.out.println("projectPath : ## " + projectPath);

        String savedFileName = uuid + "_" + oriImgName; // 파일명 -> imgName

        imgName = savedFileName;

        File saveFile = new File(projectPath, imgName);

        imgFile.transferTo(saveFile);

        item.setImgName(imgName);
        item.setImgPath("/files/" + imgName);
        user.addItem(item);

        itemRepository.save(item);
    }

    // 상품 리스트 불러오기
    public List<Item> allItemView() {
        return itemRepository.findAll();
    }
}
