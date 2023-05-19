package com.example.creditsystem.config;

import com.example.creditsystem.enums.Role;
import com.example.creditsystem.service.UsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsersDetailsService usersDetailsService;

    @Autowired
    public SecurityConfig(UsersDetailsService usersDetailsService) {
        this.usersDetailsService = usersDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/client/private/**").hasAnyRole("CLIENT", "ADMIN")
                        .requestMatchers("/worker/**").hasAnyRole("WORKER", "ADMIN")
                        .requestMatchers("/auth/**", "/admin/login", "/client/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/client")
                        .loginProcessingUrl("/login")
                        .successHandler(authenticationSuccessHandler())
                        .failureUrl("/client?error"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/client?logout")
                        .deleteCookies()
                        .clearAuthentication(true)
                );
        http
                .userDetailsService(usersDetailsService);
        return http.build();
    }


    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            List<? extends GrantedAuthority> list = authentication.getAuthorities().stream().toList();
            request.setAttribute("authorities", authentication.getAuthorities());
            if (list.get(0).equals(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name()))) {
                response.sendRedirect("/admin");
            } else if (list.get(0).equals(new SimpleGrantedAuthority(Role.ROLE_CLIENT.name()))) {
                response.sendRedirect("/client/private/cabinet");
            } else if (list.get(0).equals(new SimpleGrantedAuthority(Role.ROLE_WORKER.name()))) {
                response.sendRedirect("/worker");
            } else {
                response.sendRedirect("/client");
            }
        };
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}