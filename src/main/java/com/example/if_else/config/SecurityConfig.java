package com.example.if_else.config;

import com.example.if_else.Servises.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountService userService;

    @Bean
    PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setPasswordEncoder(bcryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userService);

        return daoAuthenticationProvider;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //todo
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/accounts/{accountId}").permitAll()
                .antMatchers(HttpMethod.GET, "/animals/{animalId}").permitAll()
                .antMatchers(HttpMethod.GET, "/animals/types/{typeId}").permitAll()
                .antMatchers(HttpMethod.GET, "/locations/{pointId}").permitAll()
                .antMatchers(HttpMethod.GET, "/animals/search").permitAll()
                .antMatchers(HttpMethod.GET, "/accounts/search").permitAll()
                .antMatchers(HttpMethod.GET, "/animals/{animalId}/locations").permitAll()
                .antMatchers(HttpMethod.POST, "/registration").not().authenticated()
                .anyRequest().authenticated()
                .and().httpBasic();
    }

}