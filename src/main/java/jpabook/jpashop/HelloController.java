package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) { // model : 데이터를 view에 넘기는 역할을 함
        model.addAttribute("data", "hello");
        return "hello"; // return "hello.html"; 과 동일
    }
}
