package com.mysite.wooju;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import com.mysite.wooju.user.UserSecurityService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration //환경설정 파일임을 의미하느 애너테이션 
@EnableWebSecurity //모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션 
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig {
	
	
	private final UserSecurityService userSecurityService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http.authorizeRequests().antMatchers("/**").permitAll() //인증되지 않은 요청허락
        .and()
            .csrf().ignoringAntMatchers("/h2-console/**")
        .and()
            .headers()
            .addHeaderWriter(new XFrameOptionsHeaderWriter(
                    XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
        .and()
        	.formLogin()
        	.loginPage("/user/login")
        	.defaultSuccessUrl("/sale/list")
        .and()
        	.logout()
        	.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
        	.logoutSuccessUrl("/")
        	.invalidateHttpSession(true)
        ;
        
    return http.build();
}

@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
}
}
