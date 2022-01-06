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
            .dataSource(dataSource)
            .passwordEncoder(passwordEncoder)
            .usersByUsernameQuery("select username, password, enabled from users where username=?")
            .authoritiesByUsernameQuery("select username, authority from authorities where username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeRequests()

            .mvcMatchers("/sign-up")
            .not()
            .authenticated()

            .mvcMatchers("/sign-in")
            .not()
            .authenticated()

            .mvcMatchers("/sign-out")
            .authenticated()

            .mvcMatchers("/index")
            .permitAll()

            .mvcMatchers("/page")
            .authenticated()

            .anyRequest().authenticated()
            .and()
            .httpBasic()
            .and()
            .formLogin()
            .loginPage("/sign-in")
            .successForwardUrl("/page")
            .and()
            .logout()
            .logoutUrl("/sing-out")
            .logoutSuccessUrl("/sign-in")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID");

        http.csrf()
            .ignoringAntMatchers("/h2-console/**");
        http.headers()
            .frameOptions()
            .sameOrigin();

//
//        http
//            .csrf()
//            .and()
//            .authorizeRequests()
//
//            .mvcMatchers("/", "/page")
//            .permitAll()
//
//            .mvcMatchers("/sign-up")
//            .permitAll()
//
//            .anyRequest().authenticated()
//            .and()
//            .formLogin()
//            .loginPage("/sign-in")
//
//            //.loginProcessingUrl("/perform_login")
//            //.successForwardUrl("/page")
//            .failureForwardUrl("/page")
//
//            //.and()
//            //.logout()
//            //.logoutSuccessUrl("/sign-out")
//            .permitAll();

    }
}