package com.afilm.wedding.controller;

import com.afilm.security.config.auth.PrincipalDetails;
import com.afilm.security.model.User;
import com.afilm.security.repository.UserRepository;
import com.afilm.wedding.domain.Image;
import com.afilm.wedding.dto.BoardDto;
import com.afilm.wedding.dto.ImagesCreationDto;
import com.afilm.wedding.dto.MarryInfoDto;
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

@Controller
@AllArgsConstructor
//@RequestMapping("board")    // /board 경로로 들어오는 경우 아래의 Method들로 분기될 수 있도록 설정
public class BoardController {
    private BoardService boardService;
    private MarryInfoService marryInfoService;
    private ImageService imageService;
    private ModelMapper modelMapper;
    private UserRepository userRepository;



    @GetMapping("/login")
    public String naverLogin(
            Authentication authentication,
            @AuthenticationPrincipal OAuth2User oauth) {
        if(authentication==null){
            return "redirect:/oauth2/authorization/naver";
        }
        return "redirect:/";
    }



    @GetMapping("/")
    public String index(@AuthenticationPrincipal PrincipalDetails userDetails,
                        Model model) {
        User user = userDetails.getUser();

        model.addAttribute("user", user);

        return "index";
    }





    @GetMapping("/imgupload")
    public String imgupload(@AuthenticationPrincipal PrincipalDetails userDetails,
                       Model model){
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        return "/img_upload";
    }

    @PostMapping("/marry-info")
    public String insertMarryInfo(MarryInfoDto marryInfoDto, @AuthenticationPrincipal PrincipalDetails userDetails) {
        User user = userDetails.getUser();
        marryInfoService.savePost(marryInfoDto,user.getId());
        return "redirect:/imgupload";
    }

    //이미지 등록
    @PostMapping("/img/upload")
    public String saveImage(Image image,MultipartFile imgFile, @AuthenticationPrincipal PrincipalDetails userDetails, Model model) throws Exception {
        User user = userDetails.getUser();


        imageService.saveImage(image, imgFile,user);

            model.addAttribute("item",user.getImages());
            return "redirect:/main";

    }


    //이미지 등록
    @PostMapping("/imgs/upload")
    public String saveImages(Image image, MultipartFile file1,
                             MultipartFile file2,MultipartFile file3,MultipartFile file4,MultipartFile file5,MultipartFile file6,MultipartFile file7,MultipartFile file8,MultipartFile file9,MultipartFile file10,MultipartFile file11,MultipartFile file12,MultipartFile file13,MultipartFile file14,MultipartFile file15,MultipartFile file16,MultipartFile file17,MultipartFile file18,MultipartFile file19,MultipartFile file20,MultipartFile file21,MultipartFile file22,MultipartFile file23,MultipartFile file24,MultipartFile file25,MultipartFile file26,MultipartFile file27,MultipartFile file28,MultipartFile file29,MultipartFile file30
            , @AuthenticationPrincipal PrincipalDetails userDetails, Model model) throws Exception {
        User user = userDetails.getUser();
        String userEmail = user.getEmail();
        int idx = userEmail.indexOf("@");

        String mail = userEmail.substring(idx+1);

        List<MultipartFile> files = new ArrayList<>();
        files.add(file1);files.add(file2);files.add(file3);files.add(file4);files.add(file5);files.add(file6);files.add(file7);files.add(file8);files.add(file9);files.add(file10);files.add(file11);files.add(file12);files.add(file13);files.add(file14);files.add(file15);files.add(file16);files.add(file17);files.add(file18);files.add(file19);files.add(file20);files.add(file21);files.add(file22);files.add(file23);files.add(file24);files.add(file25);files.add(file26);files.add(file27);files.add(file28);files.add(file29);files.add(file30);



        List<Image> images = new ArrayList<>();
        for(int i=0; i<files.size(); i++){
            Image img = imageService.getImage(i,files.get(i),mail);
            user.addImage(img);
            images.add(img);
        }
        imageService.saveAll(images);


//        imageService.saveAll(image.getImages());

        //model.addAttribute("imageList",imageService.findByUserId(user.getId()));
        return "redirect:/end";

    }


    @GetMapping("/end")
    public String getImages(@AuthenticationPrincipal PrincipalDetails userDetails,
                       Model model){
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        return "/end";
    }

    // 게시물 상세 페이지이며, {no}로 페이지 넘버를 받는다.
    // PathVariable 애노테이션을 통해 no를 받음

    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);

        model.addAttribute("boardDto", boardDTO);
        return "board/detail";
    }

    // 게시물 수정 페이지이며, {no}로 페이지 넘버를 받는다.

    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);

        model.addAttribute("boardDto", boardDTO);
        return "board/update";
    }

    // 위는 GET 메서드이며, PUT 메서드를 이용해 게시물 수정한 부분에 대해 적용

    @PutMapping("/post/edit/{no}")
    public String update(BoardDto boardDTO) {
        boardService.savePost(boardDTO);

        return "redirect:/board/list";
    }

    // 게시물 삭제는 deletePost 메서드를 사용하여 간단하게 삭제할 수 있다.

    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no) {
        boardService.deletePost(no);

        return "redirect:/board/list";
    }

    // 검색
    // keyword를 view로부터 전달 받고
    // Service로부터 받은 boardDtoList를 model의 attribute로 전달해준다.

    @GetMapping("/board/search")
    public String search(@RequestParam(value="keyword") String keyword, Model model) {
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword);

        model.addAttribute("boardList", boardDtoList);

        return "board/list";
    }
}