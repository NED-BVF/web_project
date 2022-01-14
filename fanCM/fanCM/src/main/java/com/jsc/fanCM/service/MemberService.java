package com.jsc.fanCM.service;

import com.jsc.fanCM.config.Role;
import com.jsc.fanCM.dao.MemberRepository;
import com.jsc.fanCM.domain.Member;
import com.jsc.fanCM.dto.Member.MemberSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByLoginId(username).get();
    }

    /**
     * 회원 중복 확인
     * @param loginId
     * @param nickname
     * @param email
     */
    @Transactional
    public void isDuplivateMember(String loginId, String nickname, String email) {
        if(memberRepository.existByLoginId(loginId)) {
            throw new IllegalStateException("이미 존재하는 아이디 입니다.");
        }
        else if(memberRepository.existByNickname(nickname)) {
            throw new IllegalStateException("이미 존재하는 닉네임 입니다.");
        }
        else if(memberRepository.existByEmail(email)) {
            throw new IllegalStateException("이미 존재하는 이메일 입니다.");
        }
    }

    /**
     *  회원가입
     */
    @Transactional
    public void save(MemberSaveForm memberSaveForm) throws IllegalStateException{
        isDuplivateMember(
                memberSaveForm.getLoginId(),
                memberSaveForm.getNickname(),
                memberSaveForm.getEmail()
        );
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Member member = Member.createMember(
                memberSaveForm.getLoginId(),
                bCryptPasswordEncoder.encode(memberSaveForm.getLoginPw()),
                memberSaveForm.getName(),
                memberSaveForm.getNickname(),
                memberSaveForm.getEmail(),
                Role.MEMBER
        );

        memberRepository.save(member);
    }
}
