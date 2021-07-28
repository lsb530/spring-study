package com.springstudy.springstudy.jwtPractice.config;

import com.springstudy.springstudy.jwtPractice.jwt.JwtAccessDeniedHandler;
import com.springstudy.springstudy.jwtPractice.jwt.JwtAuthenticationEntryPoint;
import com.springstudy.springstudy.jwtPractice.jwt.JwtSecurityConfig;
import com.springstudy.springstudy.jwtPractice.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
// @PreAuthroize 어노테이션을 메소드 단위로 추가하기 위해서 적용
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 추가적인 설정을 위해서 WebSecurityConfigurer 를 Implements 하거나
    // WebSecurityConfigurerAdapter 를 extends 하는 방법이 있다
    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
        TokenProvider tokenProvider,
        CorsFilter corsFilter,
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
        JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http
            // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
            .csrf().disable()

            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)

            // enable h2-console
            .and()
            .headers()
            .frameOptions()
            .sameOrigin()

            // 세션을 사용하지 않기 때문에 STATELESS로 설정
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()
            // HttpServletRequest 를 사용하는 요청들에 대한 접근제한을 설정하겠다는 의미
            .antMatchers("/api/hello").permitAll()
            // /api/hello 에 대한 요청은 인증없이 접근을 허용하겠다는 의미
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/signup").permitAll()
            // 나머지 요청들에 대해서는 모두 인증이 되어야 한다는 의미
            .anyRequest().authenticated()

            .and()
            .apply(new JwtSecurityConfig(tokenProvider));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
//        super.configure(web);
        // h2-console 하위 모든 요청들과 파비콘 관련 요청은 SpringSecurity 로직을 수행하지 않도록
        web
            .ignoring()
            .antMatchers(
                "/h2-console/**"
                ,"/favicon.ico"
                ,"/error"
            );
    }
}