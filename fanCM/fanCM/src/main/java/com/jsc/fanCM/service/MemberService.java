package com.jsc.fanCM.service;

import com.jsc.fanCM.config.Role;
import com.jsc.fanCM.dao.MemberRepository;
import com.jsc.fanCM.domain.Member;
import com.jsc.fanCM.dto.member.MemberModifyForm;
import com.jsc.fanCM.dto.member.MemberSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    if(memberRepository.existsByLoginId(loginId)) {
            throw new IllegalStateException("이미 존재하는 아이디 입니다.");
        }
        else if(memberRepository.existsByNickname(nickname)) {
            throw new IllegalStateException("이미 존재하는 닉네임 입니다.");
        }
        else if(memberRepository.existsByEmail(email)) {
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

    public Member findByLoginId(String loginId) throws IllegalStateException{
        Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);

        memberOptional.orElseThrow(
                () -> new IllegalStateException("존재하지 않는 회원 입니다.")
        );
        return memberOptional.get();
    }

    //회원정보 수정
    @Transactional
    public Long modifyMember(MemberModifyForm memberModifyForm, String loginId) {

        Member findMember = findByLoginId(loginId);

        BCryptPasswordEncoder bCryptPasswordEncoder =  new BCryptPasswordEncoder();

        findMember.modifyMember(
                bCryptPasswordEncoder.encode(memberModifyForm.getLoginPw()),
                memberModifyForm.getNickname(),
                memberModifyForm.getEmail()
        );

        return findMember.getId();

    }
}
