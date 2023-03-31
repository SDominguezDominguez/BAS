package com.example.BAS.config;

import com.example.BAS.filter.JwtRequestFilter;
import com.example.BAS.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        SessionManagementConfigurer<HttpSecurity> httpSecuritySessionManagementConfigurer = http
                .csrf().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/companies/**").hasAnyRole("ADMIN", "EMPLOYEE")
                .antMatchers(HttpMethod.POST, "/companies").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/companies/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/companies/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/customers/advisors/**").hasAnyRole("ADMIN", "EMPLOYEE", "ADVISOR")
                .antMatchers("/customers/**").hasAnyRole("ADMIN", "EMPLOYEE")
                .antMatchers("/files/**").hasAnyRole("ADMIN", "EMPLOYEE")
                .antMatchers("/policies/**").hasAnyRole("ADMIN", "EMPLOYEE")
                .antMatchers("/users/**").hasRole("ADMIN")
                .antMatchers("/authenticated").authenticated()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/**").permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
