package com.example.splitwise.сonfig;

import com.example.splitwise.auth.LoginAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    void configureGlobal(
        AuthenticationManagerBuilder auth,
        LoginAuthenticationProvider authenticationProvider
    ) throws Exception {
        auth.authenticationProvider(authenticationProvider)
            .jdbcAuthentication()
            .passwordEncoder(passwordEncoder)
            .dataSource(dataSource)
            .usersByUsernameQuery("select username, password, enabled from account")
            .authoritiesByUsernameQuery("select username, role from account");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/sign-in")
            //.loginProcessingUrl("/perform_login")
            .usernameParameter("user") // authenticate user (need users from db)
            .passwordParameter("password")
            //.successForwardUrl("/page")
            .permitAll()
            .and()
            .logout()
            .logoutUrl("/sing-out")
            .logoutSuccessUrl("/sing-in?sing-out")
            .permitAll();
    }
}