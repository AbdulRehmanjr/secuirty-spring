package com.secuirty.secuirtymanagement.config;


import com.secuirty.secuirtymanagement.service.implement.userDetailServiceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class  securityConfig {


    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private userDetailServiceimpl uds;

    
    // create secuirty configureation class with new spring update
    
    @Bean
    public PasswordEncoder passwordEncoder()  {
        return new BCryptPasswordEncoder();
    }
    
    
   @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{

        return config.getAuthenticationManager();
    }
    @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth
            .userDetailsService(this.uds);

        }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
        .csrf().disable()
        .anonymous()
        .and()
        .authorizeRequests()
        .antMatchers("/user/add","/user/show","/token/generate-token","/token/current-user").permitAll()
        .antMatchers("/user/protected","/user/all").hasAnyAuthority("ADMIN")
        .antMatchers(HttpMethod.OPTIONS).permitAll()
        .anyRequest().authenticated()
        .and()
        .cors()
        .and()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    // @Bean
    // public WebSecurityCustomizer webSecurityCustomizer(){
    //     return (web)->{
    //         web.ignoring().antMatchers();
    //     };
    // }

  
        

}
    
    



  

    
    
    
    
