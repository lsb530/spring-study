package com.springstudy.springstudy.lombokPractice.domain;

import java.time.LocalDate;
import java.util.logging.Level;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import lombok.experimental.SuperBuilder;
import lombok.extern.java.Log;

@Getter
@Setter
@ToString
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
//@Builder
@SuperBuilder
//@Slf4j
@Log
@EqualsAndHashCode(callSuper = true)
@Data
public class Member extends User {
    private String gender;

    @Exclude
    private LocalDate birth;

    private String bio;

    public static void main(String[] args) {
        int a = 3;
        log.info("정보일껄?");
        log.log(Level.INFO,"keykey",NullPointerException.class);
        log.severe("이건 좀심각하네ㅋㅋ");
        log.warning("경고임마 알겠어?");
    }
}