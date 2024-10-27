package org.example.practicaspringsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
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
                    .successHandler(successHandler())//Url a donde irá después de iniciar sesión (mirar successHandler)
                    .permitAll()
                .and()
                .sessionManagement()
                    //Política de creación de sesión. Always crea sesión siempre que no exista, if solo si se necesita,
                    //Never no crea, pero si ya existe la utiliza. Stateless no trabaja con sesiones
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) //Always -If_Required - never - stateless
                    .invalidSessionUrl("/login") //url invalido se va a login
                    .maximumSessions(1) //Sirve para cuando se pueden tener varias sesiones
                    .expiredUrl("/login") //End point para cuando se acaba la sesión
                    .sessionRegistry(sessionRegistry()) //Definir un objeto que se encarga de administrar los registros de la sesión
                .and()
                .sessionFixation()
                    //Si se detecta un ataque de fijación de sesión, Spring genera otro id de sesión
                    .migrateSession() //migrateSession - newSession - none .
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
    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl(); //Se habilita este objeto para el rastro de los datos del usuario
    }

    public AuthenticationSuccessHandler successHandler(){
        return ((request, response, auth) -> {
            response.sendRedirect("/v1/index");
        });
    }


}

