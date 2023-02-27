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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("board")    // /board 경로로 들어오는 경우 아래의 Method들로 분기될 수 있도록 설정
public class BoardController {
    private BoardService boardService;
    private MarryInfoService marryInfoService;
    private ItemService itemService;
    private ModelMapper modelMapper;
    private UserRepository userRepository;

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

        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);
        System.out.println("#########################user.getUsername() :" + user.getUsername());


        model.addAttribute("user", user);
        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);

        return "board/list";
    }

    // 글쓰는 페이지

    @GetMapping("/post")
    public String write() {
        return "board/write";
    }

    // 글을 쓴 뒤 POST 메서드로 글 쓴 내용을 DB에 저장
    // 그 후에는 /list 경로로 리디렉션해준다.

    @PostMapping("/post")
    public String write(BoardDto boardDto) {
        boardService.savePost(boardDto);
        return "redirect:/board/list";
    }


    @GetMapping("/main")
    public String main(Model model){

        List<Item> items = itemService.allItemView();
        model.addAttribute("items", items);

        return "/main";
    }

    @PostMapping("/marry-info")
    public String insertMarryInfo(MarryInfoDto marryInfoDto, @AuthenticationPrincipal PrincipalDetails userDetails) {

        System.out.println("###여기가두번찍히는거냐??");
        User user = userDetails.getUser();

        marryInfoService.savePost(marryInfoDto,user.getId());
        return "redirect:/board/list";
    }

    // 상품 등록 (POST) - 판매자만 가능
    @PostMapping("/item/new/pro")
    public String itemSave(Item item, MultipartFile imgFile) throws Exception {

            itemService.saveItem(item, imgFile);

            return "redirect:/board/main";

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