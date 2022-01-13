package com.jsc.fanCM.controller;

import com.jsc.fanCM.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
}
