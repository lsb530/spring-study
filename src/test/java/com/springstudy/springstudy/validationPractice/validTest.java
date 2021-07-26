package com.springstudy.springstudy.validationPractice;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class validTest {

    private ValidatorFactory factory;
    private Validator validator;

    @BeforeEach
    void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @DisplayName("@Notnull id 유저의 아이디는 null 일 수 없다")
    @Test
    void test0() {
        UserRequest nullIdUser = UserRequest.builder()
            .id(null)
            .name("하하")
            .nick("닉네임")
            .weight(1000)
            .accept(true)
            .fDate(LocalDateTime.now().plusMinutes(1))
            .pDate(LocalDateTime.now().minusDays(1))
            .height(250)
            .negative(-3)
            .homepage("https://www.naver.com")
            .build();
        for (int i = 0; i < 200; i++) { // size가 200까지만 허용됨
            nullIdUser.getNumbers().add(i);
        }
        System.out.println("nullIdUser.getNumbers().size() = " + nullIdUser.getNumbers().size());
        Set<ConstraintViolation<UserRequest>> constraintViolations = validator.validate(nullIdUser);

        assertEquals(1, constraintViolations.size());
    }

    @DisplayName("@NotEmpty 유저에는 이름이 null 일 수 없다")
    @Test
    void test1() {
        UserRequest noNameUser = UserRequest.builder()
            .id(1L)
            .name(null)
            .build();

        Set<ConstraintViolation<UserRequest>> constraintViolations = validator.validate(noNameUser);
        ConstraintViolation<UserRequest> violation = constraintViolations.iterator().next();

        assertAll(
            () -> assertEquals(1, constraintViolations.size()),
            () -> assertEquals("name", violation.getPropertyPath().toString()),
            () -> assertEquals("이름은 null값이 되거나 공백일 수 없습니다.", violation.getMessage())
        );
    }

    @DisplayName("@NotEmpty 유저에는 이름이 ''일 수 없다")
    @Test
    void test2() {
        UserRequest noNameUser = UserRequest.builder()
            .id(1L)
            .name("")
            .build();

        Set<ConstraintViolation<UserRequest>> constraintViolations = validator.validate(noNameUser);
        ConstraintViolation<UserRequest> violation = constraintViolations.iterator().next();

        assertAll(
            () -> assertEquals("name", violation.getPropertyPath().toString()),
            () -> assertEquals("이름은 null값이 되거나 공백일 수 없습니다.", violation.getMessage())
        );
    }

    @DisplayName("@NotEmpty 하지만 유저에는 이름이 ' '일 수는 있다")
    @Test
    void test3() {
        UserRequest noNameUser = UserRequest.builder()
            .id(1L)
            .name(" ")
            .build();

        Set<ConstraintViolation<UserRequest>> constraintViolations = validator.validate(noNameUser);

        assertAll(
            () -> assertEquals(0, constraintViolations.size())
        );
    }

    @DisplayName("@NotBlank 닉네임은 null 또는 공백일 수 없다(최소 1자 이상)")
    @Test
    void test4() {
        UserRequest nickTestUser = UserRequest.builder()
            .id(1L)
            .name("test")
            .build();
        Set<ConstraintViolation<UserRequest>> constraintViolations;

        nickTestUser.setNick(null);
        constraintViolations = validator.validate(nickTestUser);
        assertEquals(1, constraintViolations.size());

        nickTestUser.setNick("");
        constraintViolations = validator.validate(nickTestUser);
        assertEquals(1, constraintViolations.size());

        nickTestUser.setNick(" ");
        constraintViolations = validator.validate(nickTestUser);
        assertEquals(1, constraintViolations.size());

        nickTestUser.setNick("닉네임");
        constraintViolations = validator.validate(nickTestUser);
        assertEquals(0, constraintViolations.size());
    }

    @DisplayName("@PositiveOrZero 나이는 0이상이어야 한다")
    @Test
    void test5() {
        UserRequest negativeAgeUser = UserRequest.builder()
            .id(1L)
            .name("테스트")
            .nick("닉네임")
            .build();
        negativeAgeUser.setAge(-1);

        Set<ConstraintViolation<UserRequest>> constraintViolations = validator
            .validate(negativeAgeUser);
        ConstraintViolation<UserRequest> violation = constraintViolations.iterator().next();

        assertAll(
            () -> assertEquals("age", violation.getPropertyPath().toString()),
            () -> assertEquals("나이는 0 이상이어야 합니다.", violation.getMessage())
        );
    }

    @DisplayName("@Email은 이메일 형식이어야한다.")
    @Test
    void test6() {
        UserRequest emailTester = UserRequest.builder()
            .id(1L)
            .name("테스트")
            .nick("닉네임")
            .build();
        Set<ConstraintViolation<UserRequest>> constraintViolations;

        emailTester.setEmail("aaaa");
        constraintViolations = validator.validate(emailTester);
        assertEquals(1, constraintViolations.size());

        emailTester.setEmail("aaaa@bbb");
        constraintViolations = validator.validate(emailTester);
        assertEquals(0, constraintViolations.size());

        emailTester.setEmail("aaaa@ttt.com");
        constraintViolations = validator.validate(emailTester);
        assertEquals(0, constraintViolations.size());
    }


}