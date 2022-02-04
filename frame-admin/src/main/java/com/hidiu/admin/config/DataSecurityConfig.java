package com.hidiu.admin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
public class DataSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();   //无加密
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("fancie").password("liuyingxiang").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin() //表单登录
                .loginPage("/login") //登录页面
                .defaultSuccessUrl("/").permitAll() //登录成功之后的地址
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/**").permitAll()    //不需要登录就可以访问的页面
                .and()
                .authorizeRequests()
                .antMatchers("/**") //授权admin的资源
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable();
    }
}
