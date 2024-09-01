package com.estore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests(
                        (httpRequestConfigurer) -> httpRequestConfigurer
                                .requestMatchers("/addProduct").authenticated()
                                .anyRequest().permitAll())
                .formLogin(
                        (formLoginConfigurer) -> formLoginConfigurer.loginPage("/login").usernameParameter("email").passwordParameter("password").defaultSuccessUrl("/store", true).failureUrl("/login?error=true").permitAll())
                .logout(
                        (logoutConfigurer) -> logoutConfigurer.logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll());
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT email, password, true FROM seller WHERE email = ?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT email, \"Seller\" FROM seller WHERE email = ?");
        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
