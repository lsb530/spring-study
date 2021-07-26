package com.springstudy.springstudy.validationPractice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Negative;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserRequest {

    @NotNull
    private Long id;

    // null과 ""은 막히지만, " "은 허용
    @NotEmpty(message = "이름은 null값이 되거나 공백일 수 없습니다.")
    private String name;

    // null과 "", " " 모두 허용하지 않는다(가장 강력)
    @NotBlank(message = "닉네임은 null과 ''일수 없다.(최소 1자 이상)")
    private String nick;

    @PositiveOrZero(message = "나이는 0 이상이어야 합니다.")
    private int age;

    @Email(message = "이메일 형식으로 입력해주세요")
    private String email;

    //    @Size(min = 1, max = 200) // Collection에만 붙일 수 있다
    @DecimalMax(value = "1000") // <= 1000
//    @DecimalMax(value = "1000", inclusive = false) // < 1000
    @Builder.Default
    private int weight = 30;

    @AssertTrue
    private boolean accept;

    @Future // 날짜가 미래인지 확인
    private LocalDateTime fDate;

    @Past // 날짜가 과거인지 확인
    private LocalDateTime pDate;

    @Max(value = 250)
    @Min(value = 1)
    private int height; // 1 <= x <= 250

    @Negative // 값이 음수인지 확인
    private int negative;

    @Builder.Default
    @Size(min = 0, max = 200) // Collection의 사이즈 체크
    private List<Integer> numbers = new ArrayList<>();

    // 정규식 체크
    @Pattern(regexp = "^(http|https)://[a-zA-Z0-9\\-.]+\\.[a-zA-Z]{2,6}(/\\S*)?$")
    private String homepage;
}