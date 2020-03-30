package com.example.authentification.security;

import com.example.authentification.service.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    DataSource dataSource;
    public SecurityConfiguration(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Autowired
    @Qualifier("customUserDetailsService")
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity auth) throws Exception {

        auth.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/users/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .anyRequest().authenticated();

        auth.headers().frameOptions().sameOrigin();
        auth.addFilter(new JsonAuthenticationFilter(authenticationManager(), new ObjectMapper()));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }


    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

     /*
    @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .withDefaultSchema()
                .withUser(
                        User.withUsername("admin")
                                .password(passwordEncoder().encode("admin"))
                                .authorities(AuthorityUtils.createAuthorityList("ADMIN")).build()
                )
                .withUser(
                        User.withUsername("ndezerce")
                            .password(passwordEncoder().encode("ndezerce"))
                            .authorities(AuthorityUtils.createAuthorityList("USER")).build()
                );
        auth.userDetailsService(jdbcUserDetailsManager());
    }
    */
}
