package com.example.splitwise.сonfig;

import com.example.splitwise.auth.Authority;
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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(DataSource dataSource,
                          PasswordEncoder passwordEncoder) {
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

        http
            .csrf()
            .requireCsrfProtectionMatcher(new RequestHeaderRequestMatcher("/api/**"))
            .csrfTokenRepository(
                CookieCsrfTokenRepository.withHttpOnlyFalse()
            )

            .and()
            .headers()
            .frameOptions()
            .sameOrigin()
            .and()
            .sessionManagement()
            .sessionAuthenticationStrategy(new SessionFixationProtectionStrategy())

            .and()
            .authorizeRequests()
            .mvcMatchers("/error")
            .permitAll()
            .mvcMatchers("/sign-in")
            .permitAll()
            .mvcMatchers("/sign-up")
            .permitAll()
            .mvcMatchers("/**", "/dashboard/**")
            .authenticated()
            .mvcMatchers("/admin-page")
            .hasAuthority(Authority.ADMIN.getNumVal().toString())
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
            .logout()
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .logoutUrl("/signout")
            .logoutSuccessUrl("/sign-in?signout")
            .permitAll()
            .and()
            .exceptionHandling().accessDeniedPage("/access-denied-page");

    }
}