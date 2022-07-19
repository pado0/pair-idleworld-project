package com.pado.idleworld.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // todo: disable 하지말고 postman에서 토큰 처리하는 법 확인하기
                .authorizeRequests()
                // 그냥 허용할 응답들
                .mvcMatchers("/**", "/index", "/login", "/sign-up", "/operation", "/operation/history", "/api/sign-up")
                .permitAll();
                //.anyRequest().authenticated(); // 나머지는 로그인을 해야 쓸 수 있다.


        http.csrf().disable().authorizeRequests()
                .antMatchers("/v1/sign-up","/v1/account/login").authenticated()
                .antMatchers("/v1/account/{accountEmail}").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/v1/account/login")
                //.usernameParameter("email")
                .loginProcessingUrl("/v1/account/login")   //login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인 진행
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout();*/
    }



    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/h2-console/**");
    }

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }
}