package com.afilm.wedding.controller;

import com.afilm.security.config.auth.PrincipalDetails;
import com.afilm.security.model.User;
import com.afilm.security.repository.UserRepository;
import com.afilm.wedding.domain.Item;
import com.afilm.wedding.domain.MarryInfo;
import com.afilm.wedding.dto.BoardDto;
import com.afilm.wedding.dto.MarryInfoDto;
import com.afilm.wedding.service.BoardService;
import com.afilm.wedding.service.ItemService;
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

import java.util.List;

@Controller
@AllArgsConstructor
//@RequestMapping("board")    // /board 경로로 들어오는 경우 아래의 Method들로 분기될 수 있도록 설정
public class BoardController {
    private BoardService boardService;
    private MarryInfoService marryInfoService;
    private ItemService itemService;
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
        return "/imgupload";
    }

    @PostMapping("/marry-info")
    public String insertMarryInfo(MarryInfoDto marryInfoDto, @AuthenticationPrincipal PrincipalDetails userDetails) {
        User user = userDetails.getUser();
        marryInfoService.savePost(marryInfoDto,user.getId());
        return "redirect:/imgupload";
    }

    //이미지 등록
    @PostMapping("/img/upload")
    public String itemSave(Item item, MultipartFile imgFile,@AuthenticationPrincipal PrincipalDetails userDetails,Model model) throws Exception {
        User user = userDetails.getUser();


            itemService.saveItem(item, imgFile,user);

            model.addAttribute("item",user.getItems());
            return "redirect:/main";

    }

    @GetMapping("/main")
    public String getImages(@AuthenticationPrincipal PrincipalDetails userDetails,
                       Model model){
        User user = userDetails.getUser();
        model.addAttribute("items",user.getItems());
        return "/main";
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