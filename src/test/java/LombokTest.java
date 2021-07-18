import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.springstudy.springstudy.lombokPractice.domain.Company;
import com.springstudy.springstudy.lombokPractice.domain.Member;
import com.springstudy.springstudy.lombokPractice.domain.User;
import com.springstudy.springstudy.lombokPractice.domain.UserType;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LombokTest {

    @Test
    @DisplayName("@NoArgsConstructor 테스트")
    void test1() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    @DisplayName("@AllArgsConstructor 테스트")
    void test2() {
//        User user = new User(1L,"아이디","비밀번호", UserType.valueOf("게스트"));
        User user = new User(1L, "이름", "비밀번호", UserType.GUEST);
        assertAll(
            () -> assertEquals("이름", user.getName()),
            () -> assertEquals(UserType.GUEST, user.getUserType())
        );
    }

    @Test
    @DisplayName("@RequiredArgsConstructor 테스트")
    void test3() {
        User user = new User(1L, "하하");
        assertEquals(1L, user.getId());
        assertEquals("이건 테스트임", user.getTestVariable());
    }

    @Test
    @DisplayName("@Builder 테스트")
    void test4() {
        User user = User.builder()
            .id(1L)
            .name("테스트이름")
            .password("PW")
            .userType(UserType.GUEST)
            .build();
        assertEquals(1L, user.getId());
    }

    @Test
    @DisplayName("@Builer 테스트2")
    void test5() {
        Member member = Member.builder()
            .id(1L)
            .name("테스트이름")
            .password("PW")
            .userType(UserType.LOCAL)
            .bio("멤버 소개입니다")
            .birth(LocalDate.now())
            .gender("M")
            .build();
        assertEquals("M", member.getGender());
    }

    @Test
    @DisplayName("@NonNull 테스트")
    void test6() {
        assertThrows(
            NullPointerException.class, () -> User.builder().name("아아").build()
            , "id값의 @NonNull 어노테이션으로 인해서 NullPointerException이 발생한다"
        );
    }

    @Test
    @DisplayName("@ToString 테스트")
    void test7() {
        User user = new User(2L, "안녕하세요", "비밀번호", UserType.ADMIN);
        Member member = new Member("M", LocalDate.now(), "소개");
        Company company = new Company("회사", "로고", 8);
        assertAll(
            () -> assertEquals(
                "User(id=2, name=안녕하세요, testVariable=이건 테스트임, password=비밀번호, userType=ADMIN)",
                user.toString()
            ),
            () -> assertEquals(
                "Member(gender=M, bio=소개)",
                member.toString(),
                "birth 필드는 필드에서 @Exclude로 제외시킴"
            ),
            () -> assertEquals(
                "Company(logoImage=로고)",
                company.toString(),
                "회사명과 크기는 @ToString(exclude = {'필드명','필드명'}으로 제거시킴"
            )
        );
    }

    @Test
    @DisplayName("@EqualsAndHashCode callSuper 테스트")
    void test8() {
        // Member는 User를 상속받음. id & name은 NonNull
        // @EqualsAndHashCode callSuper=true, 즉 부모의 필드값인 id와 name 까지 일치하는지 확인한다.
        Member member1 = Member.builder()
            .id(1L) // 부모클래스의 속성
            .name("이름1")
            .bio("안녕하세요") // 자식클래스의 속성
            .build();
        Member member2 = Member.builder()
            .id(1L)
            .name("이름2")
            .bio("안녕하세요")
            .build();
        Member member3 = Member.builder()
            .id(1L)
            .name("이름1")
            .bio("안녕하세요")
            .build();
        Company company1 = Company.builder()
            .id(1L)
            .name("c1")
            .companyName("회사1")
            .build();
        Company company2 = Company.builder()
            .id(2L)
            .name("c2")
            .companyName("회사1")
            .build();
        assertNotEquals(member1, member2);
        assertEquals(member1,member3);
        assertNotEquals(member1, member3);
        assertEquals(company1,company2);

    }


}