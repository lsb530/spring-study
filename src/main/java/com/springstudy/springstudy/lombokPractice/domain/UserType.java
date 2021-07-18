package com.springstudy.springstudy.lombokPractice.domain;

public enum UserType {
    ADMIN("관리자"),
    GUEST("게스트"),
    LOCAL("이메일인증자"),
    SOCIAL("소셜로그인");

    private final String name;

    UserType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}