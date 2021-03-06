package com.jsc.fanCM.controller;

import com.jsc.fanCM.domain.Member;
import com.jsc.fanCM.dto.member.MemberSaveForm;
import com.jsc.fanCM.dto.member.MemberModifyForm;
import com.jsc.fanCM.dto.member.MemberLoginForm;
import com.jsc.fanCM.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원가입페이지 이동
     * @param model
     * @return
     */
    @GetMapping("/members/join")
    public String showJoin(Model model) {
        model.addAttribute("memberSaveForm", new MemberSaveForm());

        return "usr/member/join";
    }

    /**
     * 회원가입
     * @param memberSaveForm
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/members/join")
    public String doJoin(@Validated MemberSaveForm memberSaveForm, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors() ) {
            return "usr/member/join";
        }

        try {
            memberService.save(memberSaveForm);
        } catch (Exception e) {
            model.addAttribute("err_msg",e.getMessage());

            return "usr/member/join";
        }
        return "redirect:/";
    }

    @GetMapping("/members/login")
    public String showLogin(Model model){
        model.addAttribute("memberLoginForm", new MemberLoginForm());

        return "usr/member/login";
    }

    @GetMapping("/members/modify")
    public String showModify(Model model, Principal principal){

        Member findMember = memberService.findByLoginId(principal.getName());

        model.addAttribute("member", findMember);
        model.addAttribute("memberModifyForm", new MemberModifyForm());

        return "usr/member/modify";
    }

    @PostMapping("/members/modify")
    public String doModify(MemberModifyForm memberModifyForm, Principal principal, Model model){

        try {
            memberService.modifyMember(memberModifyForm, principal.getName());
        }catch (Exception e){
            model.addAttribute("err_msg", e.getMessage());
            return "usr/member/modify";
        }
        return "redirect:/";
    }
}
