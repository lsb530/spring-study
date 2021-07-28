package com.springstudy.springstudy.jwtPractice.controller;

import com.springstudy.springstudy.jwtPractice.dto.LoginDto;
import com.springstudy.springstudy.jwtPractice.dto.TokenDto;
import com.springstudy.springstudy.jwtPractice.jwt.JwtFilter;
import com.springstudy.springstudy.jwtPractice.jwt.TokenProvider;
import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        // authenticationToken을 이용해서 Authentication을 생성하려고
        // authenticate가 실행이 될 때, loadUserByUsername 메소드가 실행이 됩니다
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 이 결과 값으로 SecurityContextHolder에 저장을 한다
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 그 인증정보를 기반으로 토큰을 만든다
        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        // jwt 토큰을 Response Header에도 넣어주고
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        // TokenDto를 이용해서 Response Body에도 넣어줘서 리턴하게 된다
        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }
}