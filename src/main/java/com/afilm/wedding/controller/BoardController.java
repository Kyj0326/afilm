package com.afilm.wedding.controller;

import com.afilm.security.config.auth.PrincipalDetails;
import com.afilm.security.model.User;
import com.afilm.security.repository.UserRepository;
import com.afilm.wedding.domain.Image;
import com.afilm.wedding.domain.MarryInfo;
import com.afilm.wedding.dto.BoardDto;
import com.afilm.wedding.dto.ImagesCreationDto;
import com.afilm.wedding.dto.MarryInfoDto;
import com.afilm.wedding.repository.ImageRepository;
import com.afilm.wedding.service.BoardService;
import com.afilm.wedding.service.ImageService;
import com.afilm.wedding.service.MarryInfoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
//@RequestMapping("board")    // /board 경로로 들어오는 경우 아래의 Method들로 분기될 수 있도록 설정
public class BoardController {
    private BoardService boardService;
    private MarryInfoService marryInfoService;
    private ImageService imageService;
    private ImageRepository imageRepository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;



    @GetMapping("/login/new")
    public String naverLoginNew(
            Authentication authentication,
            @AuthenticationPrincipal OAuth2User oauth) {
        if(authentication==null){
            return "redirect:/oauth2/authorization/naver";
        }
        return "redirect:/new";
    }

    @GetMapping("/login/update")
    public String naverLoginUpdate(
            Authentication authentication,
            @AuthenticationPrincipal OAuth2User oauth) {
        if(authentication==null){
            return "redirect:/oauth2/authorization/naver";
        }
        return "redirect:/update";
    }

    @GetMapping("/new")
    public String newIndex(@AuthenticationPrincipal PrincipalDetails userDetails,
                        Model model) {
        User user = userDetails.getUser();

        model.addAttribute("user", user);

        return "new_index";
    }

    @GetMapping("/update")
    public String updateIndex(@AuthenticationPrincipal PrincipalDetails userDetails,
                        Model model) {
        User user = userDetails.getUser();

        MarryInfo marryInfo = marryInfoService.getMarryInfo(user.getId());

        model.addAttribute("marryInfo", marryInfo);

        model.addAttribute("user", user);

        return "update_index";
    }


    @PostMapping("/marry-info")
    public String insertMarryInfo(MarryInfoDto marryInfoDto, @AuthenticationPrincipal PrincipalDetails userDetails) {
        User user = userDetails.getUser();
        marryInfoService.savePost(marryInfoDto,user.getId());
        return "redirect:/imgupload_new";
    }


    @PutMapping("/marry-info")
    public String updateMarryInfo(MarryInfoDto marryInfoDto, @AuthenticationPrincipal PrincipalDetails userDetails) {
        User user = userDetails.getUser();
        marryInfoService.savePost(marryInfoDto,user.getId());
        return "redirect:/imgupload_update";
    }

    @GetMapping("/imgupload_new")
    public String uploadimg(@AuthenticationPrincipal PrincipalDetails userDetails,
                            Model model){
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        return "/img_upload_new";
    }

    @GetMapping("/imgupload_update")
    public String updateimg(@AuthenticationPrincipal PrincipalDetails userDetails,
                            Model model){

        User user = userDetails.getUser();

        List<Image> images = imageService.findByUserId(user.getId());

        model.addAttribute("images", images);

        model.addAttribute("user", user);

        return "/img_upload_update";
    }

    //이미지 등록
    @PostMapping("/imgs/upload")
    public String saveImages(Image image, MultipartFile file1,
                             MultipartFile file2,MultipartFile file3,MultipartFile file4,MultipartFile file5,MultipartFile file6,MultipartFile file7,MultipartFile file8,MultipartFile file9,MultipartFile file10,MultipartFile file11,MultipartFile file12,MultipartFile file13,MultipartFile file14,MultipartFile file15,MultipartFile file16,MultipartFile file17,MultipartFile file18,MultipartFile file19,MultipartFile file20,MultipartFile file21,MultipartFile file22,MultipartFile file23,MultipartFile file24,MultipartFile file25,MultipartFile file26,MultipartFile file27,MultipartFile file28,MultipartFile file29,MultipartFile file30
            , @AuthenticationPrincipal PrincipalDetails userDetails, Model model) throws Exception {
        User user = userDetails.getUser();
        String userEmail = user.getEmail();
        int idx = userEmail.indexOf("@");
        String mail = userEmail.substring(0, idx);
        List<MultipartFile> files = new ArrayList<>();
        files.add(file1);files.add(file2);files.add(file3);files.add(file4);files.add(file5);files.add(file6);files.add(file7);files.add(file8);files.add(file9);files.add(file10);files.add(file11);files.add(file12);files.add(file13);files.add(file14);files.add(file15);files.add(file16);files.add(file17);files.add(file18);files.add(file19);files.add(file20);files.add(file21);files.add(file22);files.add(file23);files.add(file24);files.add(file25);files.add(file26);files.add(file27);files.add(file28);files.add(file29);files.add(file30);

            List<Image> images = new ArrayList<>();
            for(int i=0; i<files.size(); i++){
                Image img = imageService.getImage(i,files.get(i),mail);
                user.addImage(img);
                images.add(img);
            }
        imageService.saveAll(images);
        return "redirect:/complete";
    }


    @GetMapping("/complete")
    public String getImages(@AuthenticationPrincipal PrincipalDetails userDetails,
                       Model model){
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        return "/complete";
    }

    // 위는 GET 메서드이며, PUT 메서드를 이용해 게시물 수정한 부분에 대해 적용

    @PutMapping("/imgs/upload")
    public String updateImages(Image image, MultipartFile file1,
                         MultipartFile file2,MultipartFile file3,MultipartFile file4,MultipartFile file5,MultipartFile file6,MultipartFile file7,MultipartFile file8,MultipartFile file9,MultipartFile file10,MultipartFile file11,MultipartFile file12,MultipartFile file13,MultipartFile file14,MultipartFile file15,MultipartFile file16,MultipartFile file17,MultipartFile file18,MultipartFile file19,MultipartFile file20,MultipartFile file21,MultipartFile file22,MultipartFile file23,MultipartFile file24,MultipartFile file25,MultipartFile file26,MultipartFile file27,MultipartFile file28,MultipartFile file29,MultipartFile file30
            , @AuthenticationPrincipal PrincipalDetails userDetails, Model model) throws Exception {
        User user = userDetails.getUser();
        String userEmail = user.getEmail();
        int idx = userEmail.indexOf("@");
        String mail = userEmail.substring(0, idx);
        List<MultipartFile> files = new ArrayList<>();
        files.add(file1);files.add(file2);files.add(file3);files.add(file4);files.add(file5);files.add(file6);files.add(file7);files.add(file8);files.add(file9);files.add(file10);files.add(file11);files.add(file12);files.add(file13);files.add(file14);files.add(file15);files.add(file16);files.add(file17);files.add(file18);files.add(file19);files.add(file20);files.add(file21);files.add(file22);files.add(file23);files.add(file24);files.add(file25);files.add(file26);files.add(file27);files.add(file28);files.add(file29);files.add(file30);

        List<Image> images = new ArrayList<>();
        for(int i=0; i<files.size(); i++){

            //유입 된 값이 null이 아닌 것만 update치면 된다.
            if(!files.get(i).getOriginalFilename().isEmpty()) {

                Image img = imageService.getImage(i, files.get(i), mail);
                user.addImage(img);
                images.add(img);
            }
        }
        imageService.saveAll(images);

        return "redirect:/complete";
    }
}