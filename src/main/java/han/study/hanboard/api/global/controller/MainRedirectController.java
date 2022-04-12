package han.study.hanboard.api.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainRedirectController {

    @GetMapping("/")
    public String getMain() {
        return "redirect:/post";
    }
}
