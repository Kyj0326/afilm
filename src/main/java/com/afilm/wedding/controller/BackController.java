package com.afilm.wedding.controller;

import com.afilm.security.config.auth.PrincipalDetails;
import com.afilm.security.model.User;
import com.afilm.wedding.domain.Image;
import com.afilm.wedding.dto.BoardDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class BackController {

    // 게시판

    // 게시글 목록

    // list 경로로 GET 메서드 요청이 들어올 경우 list 메서드와 맵핑시킴
    // list 경로에 요청 파라미터가 있을 경우 (?page=1), 그에 따른 페이지네이션을 수행함.

    @GetMapping({"", "/list"})
    public String list(Authentication authentication,  // DI(의존성주입)
                       @AuthenticationPrincipal PrincipalDetails userDetails,
                       Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {

        System.out.println("/test/login =============");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal(); // 다운캐스팅
        System.out.println("authentication : " + principalDetails.getUser());

        // 2) @AuthenticationPrincipal 어노테이션 사용해서
        System.out.println("userDetails:" + userDetails.getUser());
        User user = userDetails.getUser();


        //model.addAttribute("userImg", user.getEmail());

//        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
//        Integer[] pageList = boardService.getPageList(pageNum);
        System.out.println("#########################user.getUsername() :" + user.getUsername());


        model.addAttribute("user", user);
//        model.addAttribute("boardList", boardList);
//        model.addAttribute("pageList", pageList);

        return "board/list";
    }


    @GetMapping("/post")
    public String write() {
        return "board/write";
    }
    // 글을 쓴 뒤 POST 메서드로 글 쓴 내용을 DB에 저장
    // 그 후에는 /list 경로로 리디렉션해준다.

    @PostMapping("/post")
    public String write(BoardDto boardDto) {
        //boardService.savePost(boardDto);
        return "redirect:/board/list";
    }

    @GetMapping("/imgupload")
    public String main(@AuthenticationPrincipal PrincipalDetails userDetails,
                       Model model){
        User user = userDetails.getUser();
//        List<Item> items = itemService.allItemView();
//        model.addAttribute("items", items);
        model.addAttribute("user", user);

        return "/imgupload";
    }

    //이미지 등록
    @PostMapping("/img/upload")
    public String saveImage(Image image, MultipartFile imgFile, @AuthenticationPrincipal PrincipalDetails userDetails, Model model) throws Exception {
        User user = userDetails.getUser();


       // imageService.saveImage(image, imgFile,user);

        model.addAttribute("item",user.getImages());
        return "redirect:/main";

    }


}
