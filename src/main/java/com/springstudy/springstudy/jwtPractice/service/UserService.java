package com.springstudy.springstudy.jwtPractice.service;

import com.springstudy.springstudy.jwtPractice.dto.UserDto;
import com.springstudy.springstudy.jwtPractice.entity.Authority;
import com.springstudy.springstudy.jwtPractice.entity.User;
import com.springstudy.springstudy.jwtPractice.repository.UserRepository;
import com.springstudy.springstudy.jwtPractice.util.SecurityUtil;
import java.util.Collections;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User signup(UserDto userDto) {
        // 1. username이 DB에 존재하지 않으면
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null)
            != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        // 빌더 패턴의 장점
        // 2. Authority 정보를 생성하고
        // * 중요포인트: signup 메서드를 통해 가입한 회원은 ROLE_USER을 가지고 있고,
        //          data.sql에서 자동 생성되는 admin 계정은 USER, ADMIN ROLE을 가지고 있습니다.
        //          이 차이를 통해 권한 검증 부분을 테스트한다
        Authority authority = Authority.builder()
            .authorityName("ROLE_USER")
            .build();

        // 3. User 정보도 생성해서
        User user = User.builder()
            .username(userDto.getUsername())
            .password(passwordEncoder.encode(userDto.getPassword()))
            .nickname(userDto.getNickname())
            .authorities(Collections.singleton(authority))
            .activated(true)
            .build();

        // 4. UserRepository에 저장
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
        // username을 기준으로 DB에서 유저객체와 권한 정보를 가져오고
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        // SecurityContext에 저장된 username의 정보만 가져온다
        return SecurityUtil.getCurrentUsername()
            .flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }
}