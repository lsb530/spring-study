package com.springstudy.springstudy.lombokPractice.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString(exclude = {"scale", "companyName"})
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
//@Builder
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class Company extends User {
    private String companyName;

    private String logoImage;

    private int scale;
}