package com.example.creditsystem.config;

import com.example.creditsystem.service.UsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
                        .loginPage("/auth")
                        .loginProcessingUrl("/admin_process_login")
                        .defaultSuccessUrl("/admin", true)
                        .failureUrl("/admin?error"))
                .formLogin(form -> form
                        .loginPage("/auth")
                        .loginProcessingUrl("/worker_process_login")
                        .defaultSuccessUrl("/worker", true)
                        .failureUrl("/worker?error"))
                .formLogin(form -> form
                        .loginPage("/client")
                        .loginProcessingUrl("/client_process_login")
                        .defaultSuccessUrl("/client/private/cabinet", true)
                        .failureUrl("/client/login?error"))
                .logout(logout -> logout
                        .logoutUrl("/logout_client")
                        .logoutSuccessUrl("/client/login?logout")
                        .deleteCookies()
                        .clearAuthentication(true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout_user")
                        .logoutSuccessUrl("/auth/login?logout")
                        .deleteCookies()
                        .clearAuthentication(true)
                );
        http
                .userDetailsService(usersDetailsService);
        return http.build();
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