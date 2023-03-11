package com.afilm.security.controller;

import com.afilm.security.config.auth.PrincipalDetails;
import com.afilm.security.model.User;
import com.afilm.security.repository.UserRepository;
import com.afilm.wedding.service.BoardService;
import com.afilm.wedding.service.ImageService;
import com.afilm.wedding.service.MarryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private BoardService boardService;

    @Autowired
    private MarryInfoService marryInfoService;

    @Autowired
    private ImageService itemService;

    @GetMapping("/test/login")
    public @ResponseBody String loginTest(
            Authentication authentication,  // DI(의존성주입)
            @AuthenticationPrincipal PrincipalDetails userDetails) {
        // 1) Authentication -> PrincipalDetails.getUser()
        System.out.println("/test/login =============");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal(); // 다운캐스팅
        System.out.println("authentication : " + principalDetails.getUser());

        // 2) @AuthenticationPrincipal 어노테이션 사용해서
        System.out.println("userDetails:" + userDetails.getUser());
        return "세션 정보 확인하기";
    }

//    @GetMapping("/test/oauth/login")
    @GetMapping("/test")
    public String testOAuthTest(
            Authentication authentication,
            @AuthenticationPrincipal OAuth2User oauth) {
        System.out.println("/test/Oauth/login =============");
        if(authentication==null){
            return "redirect:/oauth2/authorization/naver";
        }
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal(); // 다운캐스팅
        System.out.println("authentication : " + oauth2User.getAttributes());

        System.out.println("oauth2User:" + oauth.getAttributes());
        return "redirect:/board/list";
    }


//    @GetMapping({"","/"})
//    public String index(Authentication authentication,  // DI(의존성주입)
//                        @AuthenticationPrincipal PrincipalDetails userDetails,
//                        Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
//
//        System.out.println("/test/login =============");
//        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal(); // 다운캐스팅
//        System.out.println("authentication : " + principalDetails.getUser());
//
//        // 2) @AuthenticationPrincipal 어노테이션 사용해서
//        System.out.println("userDetails:" + userDetails.getUser());
//        User user = userDetails.getUser();
//
//
//        //model.addAttribute("userImg", user.getEmail());
//
//        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
//        Integer[] pageList = boardService.getPageList(pageNum);
//        System.out.println("#########################user.getUsername() :" + user.getUsername());
//
//
//        model.addAttribute("user", user);
//        model.addAttribute("boardList", boardList);
//        model.addAttribute("pageList", pageList);
//
//        return "index"; // src/main/resources/templates/index.mustache
//    }

    //일반로그인, 구글로그인 둘다 PrincipalDetails로 회원정보를 받을 수 있음
    //@AuthenticationPrincipal 어노테이션은
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principalDetails : " + principalDetails.getUser());
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    // 스프링 시큐리티가 해당주소를 낚아채버린다 - SecurityConfig파일 생성후 작동 안함
    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public  String join(User user) {
        System.out.println(user);
        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user); // 회원가입이 잘되나, 비밀번호 1234가 그대로 노출 -> 시큐리티로 로그인을 할 수 없다
                                    // 이유는 패스워드가 암호화가 되지 않았디 때문(이건 암호화한 상태)
        return "redirect:/loginForm";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @GetMapping("/info")
    public @ResponseBody String data() {
        return "데이터 정보";
    }


}