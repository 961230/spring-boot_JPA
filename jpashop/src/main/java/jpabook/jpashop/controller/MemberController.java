package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        log.info("회원가입 요청");
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            log.info("이름칸 공백 오류");
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        log.info("{ id : " + member.getId() + " 이름 : " + form.getName() + " } 회원가입 성공!");
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();

        // 엔티티를 반환하지 않고 list반환하기
//        List<MemberForm> memberForms = new ArrayList<>();
//        MemberForm m = new MemberForm();
//        for (Member member:members){
//            m.setName(member.getName());
//            m.setCity(member.getAddress().getCity());
//            m.setStreet(member.getAddress().getStreet());
//            m.setZipcode(member.getAddress().getZipcode());
//            memberForms.add(m);
//        }
//        model.addAttribute("memberForms", memberForms);

        model.addAttribute("members", members);
        return "members/memberList";
    }
}

































