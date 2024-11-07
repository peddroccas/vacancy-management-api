package br.com.peddroccas.vacancy_management.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private SecurityCandidateFilter securityCandidateFilter;
    @Autowired
    private SecurityCompanyFilter securityCompanyFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csfr -> csfr.disable()).authorizeHttpRequests(auth -> {
            auth
                    .requestMatchers("/candidate/").permitAll()
                    .requestMatchers("/candidate/auth").permitAll()
                    .requestMatchers("/company/").permitAll()
                    .requestMatchers("/company/auth").permitAll();

            auth.anyRequest().authenticated();
        })
                .addFilterBefore(securityCompanyFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
