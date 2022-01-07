package com.example.splitwise.сonfig;

import com.example.splitwise.auth.LoginAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .mvcMatchers("/resources/**");
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

        http.csrf()
            .ignoringAntMatchers("/h2-console/**");
        http.headers()
            .frameOptions()
            .sameOrigin();

        http
            .authorizeRequests()

            .antMatchers("/error")
            .permitAll()

            .antMatchers("/csrf")
            .permitAll()

            .antMatchers("/sign-in")
            .permitAll()

            .antMatchers("/sign-up")
            .permitAll()

            .antMatchers("/**", "/page/**")
            .authenticated()

            .antMatchers("/admin-page")
            .hasRole("ADMIN")

            .anyRequest().authenticated()
            .and()
            .httpBasic()
//            .and()
//            .formLogin()
//            .loginPage("/sign-up")
//            .loginProcessingUrl("/sign-up")
//            .defaultSuccessUrl("/sign-in",true)
//            .failureUrl("/sign-up?error=true")
//            .permitAll()
            .and()
            .formLogin()
            .loginPage("/sign-in")
            .loginProcessingUrl("/sign-in")
            .usernameParameter("user")
            .passwordParameter("password")
            .defaultSuccessUrl("/page",true)
            .failureUrl("/sign-in?error")
            .permitAll()
            .and()
            .logout().deleteCookies("JSESSIONID")
            .logoutUrl("/signout")
            .logoutSuccessUrl("/sign-in?signout")
            .permitAll()
            .and()
            .exceptionHandling().accessDeniedPage("/access-denied-page");


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