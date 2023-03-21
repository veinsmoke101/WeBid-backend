package com.veinsmoke.webidbackend.config;

import com.veinsmoke.webidbackend.security.JWTAuthFilter;
import com.veinsmoke.webidbackend.security.UserDetailsServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthFilter jwtAuthFilter;
    private final UserDetailsServiceImp userDetailsService;

    @Value("${path}")
    private static String path ;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers(
                            path + "/auth/admin",
                            path + "/auth",
                            path + "/register",
                            path + "/verify"
                    ).permitAll()
                    .requestMatchers("/admin/**").hasAuthority("ADMIN")
                    .requestMatchers(
                            path + "/client/**",
                            path + "/auction/**"
                    ).hasAuthority("CLIENT")
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationProvider authenticationProvider)  {
        return new ProviderManager(List.of(authenticationProvider));
    }



}
