package org.example.practicaspringsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

//    //Configuration 1
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .authorizeHttpRequests()
//                    .requestMatchers("/v1/index2").permitAll()
//                    .anyRequest().authenticated()
//                .and()
//                .formLogin().permitAll()
//                .and().
//                build();
//
//
//    }
//
//    //Para version de SpringBoot 6
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/v1/index2").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .permitAll()
//                )
//                .build();
//    }

    //Configuration 2
    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests( auth -> {
                    auth.requestMatchers("/v1/index2").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin()
                    .successHandler(successHandler())
                    .permitAll()
                .and()
                .build();
    }

    //Para version 6.1
//    @Bean
//    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .authorizeHttpRequests(auth -> auth
//                        // Permitir acceso sin autenticación a la ruta específica
//                        .requestMatchers("/v1/index2").permitAll()
//                        // Indicar que cualquier otra ruta requiere autenticación
//                        .anyRequest().authenticated()
//                )
//                .formLogin(withDefaults())  // Configuración predeterminada para el formulario de login
//                .build();
//    }

    public AuthenticationSuccessHandler successHandler(){
        return ((request, response, auth) -> {
            response.sendRedirect("/v1/index");
        });
    }


}

