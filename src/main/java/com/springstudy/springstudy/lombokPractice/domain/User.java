package com.springstudy.springstudy.lombokPractice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
//@Builder
@SuperBuilder
@RequiredArgsConstructor
public class User {
    @NonNull
    private Long id;
    @NonNull
    private String name;
    private final String testVariable = "이건 테스트임";
    private String password;
    private UserType userType;
}