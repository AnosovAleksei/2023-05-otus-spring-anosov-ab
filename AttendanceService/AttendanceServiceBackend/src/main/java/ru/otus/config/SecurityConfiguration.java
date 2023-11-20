package ru.otus.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.otus.domain.UserAccess;
import ru.otus.service.UserAccessService;

import java.util.ArrayList;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserAccessService userService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.GET, "/api/v1/team").hasAnyRole("TEACHER", "CAPTAIN")

                        .requestMatchers(
                        "/api/v1/*/control-date").hasAnyRole("TEACHER")
                        .requestMatchers(
                        "/api/v1/*/control-write-date").hasAnyRole("CAPTAIN")
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/*/student").hasAnyRole("CAPTAIN")
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/*/student").hasAnyRole("CAPTAIN")
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/*/student/*").hasAnyRole("TEACHER")

                        .requestMatchers("/swagger/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        var users = new ArrayList<UserDetails>();
        for (UserAccess user : userService.getAllUsers()) {
            users.add(User
                    .builder().username(user.getUsername()).password(user.getPassword()).roles(user.getRole())
                    .build());
        }
        return new InMemoryUserDetailsManager(users);
    }

}
