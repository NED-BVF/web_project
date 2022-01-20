package com.jsc.fanCM.dto.member;

import lombok.Data;

@Data
public class MemberModifyForm {

    private String loginPw;

    private String nickname;

    private String email;

}