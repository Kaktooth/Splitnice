package com.example.splitwise.сonfig;

import com.example.splitwise.auth.LoginAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(WebSecurity web) {
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
            .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?")
            .authoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username=?");

        auth.eraseCredentials(false);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
            .ignoringAntMatchers("/h2-console/**");
        http.headers()
            .frameOptions()
            .sameOrigin();
        http.sessionManagement()
            .sessionAuthenticationStrategy(new SessionFixationProtectionStrategy());
        http.authorizeRequests()
            .antMatchers("/error")
            .permitAll()
            .antMatchers("/sign-in")
            .permitAll()
            .antMatchers("/sign-up")
            .permitAll()
            .antMatchers("/**", "/dashboard/**")
            .authenticated()
            .antMatchers("/admin-page")
            .authenticated()
            .anyRequest().authenticated()
            .and()
            .httpBasic()
            .and()
            .formLogin()
            .loginPage("/sign-in")
            .loginProcessingUrl("/sign-in")
            .usernameParameter("user")
            .passwordParameter("password")
            .defaultSuccessUrl("/dashboard", true)
            .failureUrl("/sign-in?error")
            .permitAll()
            .and()
            .logout().deleteCookies("JSESSIONID")
            .invalidateHttpSession(true)
            .logoutUrl("/signout")
            .logoutSuccessUrl("/sign-in?signout")
            .permitAll()
            .and()
            .exceptionHandling().accessDeniedPage("/access-denied-page")
            .and()
            .rememberMe()
            .rememberMeCookieName("remember-me")
            .rememberMeParameter("remember-me")
            .key("remember-me")
            .tokenValiditySeconds(15000);
    }
}