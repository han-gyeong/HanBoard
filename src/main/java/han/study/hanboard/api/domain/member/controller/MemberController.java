package han.study.hanboard.api.domain.member.controller;

import han.study.hanboard.api.domain.member.models.MemberDto;
import han.study.hanboard.api.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final HttpServletRequest request;

    @GetMapping("/register")
    public String registerPage() {
        if (isLogin()) {
            return "redirect:/post";
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(MemberDto memberDto) {
        if (isLogin()) {
            return "redirect:/post";
        }

        memberService.createMember(memberDto);
        return "redirect:/post";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        if (isLogin()) {
            return "redirect:/post";
        }

        return "login";
    }

    @PostMapping(value = "/login")
    public String login(MemberDto memberDto) {
        boolean loginResult = memberService.login(memberDto);
        if (!loginResult) {
            return "login";
        }

        request.getSession().setAttribute("username", memberDto.getUsername());
        return "redirect:/post";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();

        return "redirect:/post";
    }

    private boolean isLogin() {
        try {
            if (StringUtils.hasText((String) request.getSession().getAttribute("username"))) {
                return true;
            }
        } catch (ClassCastException e) {
        }
        return false;
    }
}
