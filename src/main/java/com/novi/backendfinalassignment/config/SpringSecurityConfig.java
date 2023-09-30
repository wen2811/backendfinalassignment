package com.novi.backendfinalassignment.config;

import com.novi.backendfinalassignment.filter.JwtRequestFilter;
import com.novi.backendfinalassignment.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    public final CustomUserDetailsService customUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    // PasswordEncoderBean. Deze kun je overal in je applicatie injecteren waar nodig.
    // Je kunt dit ook in een aparte configuratie klasse zetten.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Authenticatie met customUserDetailsService en passwordEncoder
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }



    // Authorizatie met jwt
    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeHttpRequests()
                // Wanneer je deze uncomments, staat je hele security open. Je hebt dan alleen nog een jwt nodig.
//                .requestMatchers("/**").permitAll()

                //User
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.GET,"/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")

                //Bookings
                .requestMatchers(HttpMethod.GET, "/bookings").hasAnyRole("ADMIN","EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/bookings/{id}").hasAnyRole("ADMIN","EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "//bookings/").permitAll()
                .requestMatchers(HttpMethod.PUT, "/bookings/{id}").hasAnyRole("hasRole('ADMIN') or hasRole('EMPLOYEE') or (principal.username == #booking.customer.username)")
                .requestMatchers(HttpMethod.PUT, "/bookings/updateTreatments/{id}").hasAnyRole("hasRole('ADMIN') or hasRole('EMPLOYEE') or (principal.username == #booking.customer.username)")
                .requestMatchers(HttpMethod.DELETE, "/bookings/{id}").hasAnyRole("ADMIN","EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/bookings/{customerId}/bookings").hasAnyRole("ADMIN","EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/createWithoutRegistration\"").permitAll()

                // BookingTreatments
                .requestMatchers(HttpMethod.GET, "/bookingtreatments").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/bookingtreatments/{id}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/bookingtreatments").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.PUT, "/bookingtreatments/{id}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.DELETE, "/bookingtreatments/{id}").hasAnyRole("ADMIN", "EMPLOYEE")

                //Calendars
                .requestMatchers(HttpMethod.GET, "/calendars").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/calendars/{id}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/calendars").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.PUT, "/calendars/{id}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.DELETE, "/calendars/{id}").hasAnyRole("ADMIN", "EMPLOYEE")

                //Customers
                .requestMatchers(HttpMethod.GET, "/customers").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/customers/{id}").hasAnyRole("hasRole('ADMIN') or hasRole('EMPLOYEE') or hasPermission(#id, 'UserCredential')")
                .requestMatchers(HttpMethod.POST, "/customers").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.PUT, "/customers/{id}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.DELETE, "/customers/{id}").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/customers/{customerId}/invoices").hasAnyRole("ADMIN", "EMPLOYEE")



                //.requestMatchers(HttpMethod.DELETE, "/remotecontrollers/**").hasRole("ADMIN")
               // .requestMatchers(HttpMethod.POST, "/televisions").hasRole("ADMIN")
               // .requestMatchers(HttpMethod.DELETE, "/televisions/**").hasRole("ADMIN")
               // .requestMatchers(HttpMethod.POST, "/wallbrackets").hasRole("ADMIN")
               // .requestMatchers(HttpMethod.DELETE, "/wallbrackets/**").hasRole("ADMIN")
                // Je mag meerdere paths tegelijk definieren
               // .requestMatchers("/cimodules", "/remotecontrollers", "/televisions", "/wallbrackets").hasAnyRole("ADMIN", "USER")

                //Authentication
                .requestMatchers("/authenticated").authenticated()
                .requestMatchers("/authenticate").permitAll()
                .anyRequest().denyAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
