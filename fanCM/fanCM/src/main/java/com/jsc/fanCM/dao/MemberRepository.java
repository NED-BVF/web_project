package com.jsc.fanCM.dao;

import com.jsc.fanCM.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;


import java.lang.ref.Reference;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);

    boolean existByLoginId(String loginId);
    boolean existByNickname(String nickname);
    boolean existByEmail(String email);
}
