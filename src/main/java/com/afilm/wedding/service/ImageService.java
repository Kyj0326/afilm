package com.afilm.wedding.service;


import com.afilm.security.model.User;
import com.afilm.security.repository.UserRepository;
import com.afilm.wedding.domain.Image;
import com.afilm.wedding.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ImageService {

    private ImageRepository imageRepository;
    private UserRepository userRepository;


    public Image getImage(Integer seq, MultipartFile imgFile, String email) throws Exception{



        String path = System.getProperty("user.dir")+ "/src/main/resources/static/file/" + email + "/"; //폴더 경로
        File Folder = new File(path);

        // 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
        if (!Folder.exists()) {
            try{
                Folder.mkdir(); //폴더 생성합니다.
                System.out.println("폴더가 생성되었습니다.");
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }else {
            System.out.println("이미 폴더가 생성되어 있습니다.");
        }

        String oriImgName = imgFile.getOriginalFilename();


        // UUID 를 이용하여 파일명 새로 생성
        // UUID - 서로 다른 객체들을 구별하기 위한 클래스
        UUID uuid = UUID.randomUUID();
        System.out.println("path : ## " + path);

        String savedFileName = "";
        String imgPath = "";
        String imgName = "";
        System.out.println("oriImgName : " + oriImgName);
        if(!oriImgName.isEmpty()) {
            savedFileName = uuid + "_" + oriImgName; // 파일명 -> imgName
            File saveFile = new File(path, savedFileName);
            imgFile.transferTo(saveFile);
            imgName = savedFileName;
            imgPath = "/file/"+ email + "/" + imgName;
        }



        return Image.builder()
                .imgName(imgName)
                .imgPath(imgPath)
                .seq(seq)
                .build();
    }

    public void saveImage(Image image, MultipartFile imgFile, User user) throws Exception {


        //User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

        String oriImgName = imgFile.getOriginalFilename();
        String imgName = "";

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/file/";

        // UUID 를 이용하여 파일명 새로 생성
        // UUID - 서로 다른 객체들을 구별하기 위한 클래스
        UUID uuid = UUID.randomUUID();
        System.out.println("projectPath : ## " + projectPath);

        String savedFileName = uuid + "_" + oriImgName; // 파일명 -> imgName

        imgName = savedFileName;

        File saveFile = new File(projectPath, imgName);

        imgFile.transferTo(saveFile);
        image.setImgName(imgName);
        image.setImgPath("/files/" + imgName);
        user.addImage(image);

        imageRepository.save(image);
    }

    // 상품 리스트 불러오기
    public List<Image> allImages() {
        return imageRepository.findAll();
    }

    public void saveAll(List<Image> images) {
        imageRepository.saveAll(images);
    }

    public Object findByUserId(Long id) {
      return imageRepository.findByUserId(id);
    }
}
