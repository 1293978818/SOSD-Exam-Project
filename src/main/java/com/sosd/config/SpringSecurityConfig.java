package com.sosd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sosd.filters.JsonUsernamePasswordFilter;

/**
 * 安全框架 Spring Security 的配置类
 * @author 应国浩
 */
@Configuration
public class SpringSecurityConfig {
    
    /**
     * 配置 Spring Security 的过滤器和拦截器以及放行接口
     * @param http 使用此进行配置
     * @return 配置好的过滤器链
     * @throws Exception 可能存在的错误我们直接抛出
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,JsonUsernamePasswordFilter jsonUsernamePasswordFilter) throws Exception {

        http

            //对于所有页面需要认证
            .authorizeHttpRequests(auth -> {
                auth
                    .requestMatchers("/user/login/username").permitAll()
                    .anyRequest().authenticated();
            })

            //设置json字符串过滤器
            .addFilterAt(jsonUsernamePasswordFilter,UsernamePasswordAuthenticationFilter.class)

            //设置登录的url，认证成功处理器，认证失败处理器
            .formLogin(login -> {
                login
                    .disable();
            })
            
            // 不创建会话，因为我们使用了 Token 而不是 Session
            .sessionManagement(session -> {
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            })
            
            //同理，因为我们使用的是 Token 所以不需要 CSRF 防护
            .csrf(csrf -> {
                csrf.disable();
            });

        //构建配置好的过滤器链
        return http.build();
    }

    /**
     * 配置密码编码器
     * @return 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        //返回默认的密码编码器便于后续登录注册的密码校验
        return new BCryptPasswordEncoder();
    }

    /**
     * 用于登录认证
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
