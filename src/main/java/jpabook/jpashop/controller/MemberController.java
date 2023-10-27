package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    /*
    요구 사항이 단순할 경우에는 엔티티를 그대로 사용해도 된다.
    그러나 화면이 복잡해지기 시작하면 그에 맞추기 위해 엔티티에 화면을 처리하기 위한 기능이 계속 증가한다.
    결국 엔티티가 화면에 종속적으로 변하게 되고 코드가 지저분하게 되므로 엔티티는 핵심 비즈니스 로직만 작성

    API 개발 시에는 이유 불문하고 웹으로 엔티티 반환 절대 금지
    password 등의 정보 노출 가능성 있음. 로직 수정 시 api 스펙도 같이 변하므로 불안정한 상태가 됨.
    외부(web)로는 절대 노출 금지고 템플릿 엔진(타임리프 등)에서는 상황에 따라 선택적 사용 가능.

    DTO나 화면에 맞는 object로 변환하여 사용하는 것을 권장
    */

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) { // @Valid 내 여러 validation 옵션이 있음 (Notnull, Notempty 등등)

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/"; // 저장 후 재로딩되면 안좋으므로 보통 redirect (첫번째 페이지로 이동)
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
